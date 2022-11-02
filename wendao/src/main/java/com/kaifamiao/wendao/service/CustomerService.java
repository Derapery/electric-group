package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.*;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.Attention;
import com.kaifamiao.wendao.utils.Constants;
import com.kaifamiao.wendao.utils.LikeExplain;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private CustomerDao customerDao;
    private ExplainDao explainDao;
    private TopicsDao topicsDao;
    private AttentionDao attentionDao;
    private TopicLikeDao topicLikeDao;
    private ExplainLikeDao explainLikeDao;
    //获得一个SnowflakeIdGenerator实例
    private SnowflakeIdGenerator snowId= SnowflakeIdGenerator.getInstance();
    public CustomerService(){
        customerDao=new CustomerDao();
        explainDao=new ExplainDao();
        topicsDao=new TopicsDao();
        attentionDao=new AttentionDao();
        topicLikeDao=new TopicLikeDao();
        explainLikeDao=new ExplainLikeDao();
    }
    //查看用户是否存在
    public Customer exist(String username){
         Customer customer=customerDao.exist(username);
         return customer;
    }
    //加密码进行加密{

    public  String encrypt(String password,String sal){
        //对密码进行第一次加密
        String pass= DigestUtils.sha1Hex(password);
        //插入盐值
        StringBuilder SB=new StringBuilder(pass);
        SB.insert(20,sal);
        //对密码进行二次加密
        return DigestUtils.sha1Hex(SB.toString());
    }
    //自动生成盐值
    private String HavSal(){
        return RandomStringUtils.randomAlphabetic(5);
    }
    //添加用户
    public boolean save(Customer customer) {
        //根据雪花算法的实例获得一个ID
        customer.setId(snowId.generate());
        customer.setRegisterDate(LocalDate.now());
        String pass = customer.getPassword();
        customer.setSalt(HavSal());
        customer.setManagement(Constants.CUSTOMER_ID.getValue());
        customer.setPassword(encrypt(pass, customer.getSalt()));
        return customerDao.save(customer);
    }
    //根据ID查找用户
    public Customer find(Long along){

        Customer customer=customerDao.find(along);
        List<Topic> list= topicLikeDao.likeList(along);
        for(Topic topic : list){
            Long id=topic.getId();
            topic=topicsDao.find(id);
            topic.setThumbDownCount(topicLikeDao.thumbDownCount(id));
            topic.setThumbUpCount(topicLikeDao.thumbUPCount(id));
        }
        customer.setLikeList(list);
        //获取用户的关注的列表
        List<Customer> lists=attentionDao.findByCustomer(customer);
        customer.setAttention(lists);
        //获取用户的粉丝列表
        List<Customer> fans=attentionDao.findByFans(customer);
        customer.setFans(fans);
        return customer;
    }
    //根据用户名来查找用户

    public Customer find(String username){
        List<Topic> list=null;
        Customer customer=customerDao.findName(username);
        try{
        list= topicLikeDao.likeList(customer.getId());
        }catch (Exception e){
            Customer message = new Customer();
            message.setId(0L);
            message.setUsername("用户不存在");
            return message;
        }
        for(Topic topic : list){
            Long id=topic.getId();
            topic=topicsDao.find(id);
            topic.setThumbDownCount(topicLikeDao.thumbDownCount(id));
            topic.setThumbUpCount(topicLikeDao.thumbUPCount(id));
        }
        customer.setLikeList(list);
        //获取用户的关注的列表
        List<Customer> lists=attentionDao.findByCustomer(customer);
        System.out.println("关注："+lists.size());
        customer.setAttention(lists);
        customer.setId(2096031496380096512l);
        //获取用户的粉丝列表
        List<Customer> fans=attentionDao.findByFans(customer);
        System.out.println("粉丝"+fans.size());
        customer.setFans(fans);
        return customer;
    }

    //查询用户列表
    public List<Customer> findAll(){
        return customerDao.finaAll();
    }
    //删除用户
    public boolean delete(Long along){
        List<Topic> topics =topicsDao.findUser(along);
        //先获取用户储的评论存
        List<Explain> explains=explainDao.findCus(along);
        for(Explain explain:explains){
            LikeExplain explainLike=new LikeExplain();
            explainLike.setExplain_id(explain.getId());
            //删除每个评论的点赞信息
            explainLikeDao.delete(explain.getId(),2);
            //删除评论
            explainDao.delete(explain.getId());
        }
        //删除用户存储的话题

        for (Topic topic: topics) {
            //删除话题的相关评论
            List<Explain> explainList = explainDao.findTop(topic.getId());
            //删除话题的评论及相关的点赞信息
            for (Explain explain : explainList) {
                LikeExplain explainLike = new LikeExplain();
                explainLike.setExplain_id(explain.getId());
                //删除每个评论的点赞信息
                explainLikeDao.delete(explain.getId());
                //删除评论
                explainDao.delete(explain.getId());
            }
            //获取每个话题的点赞信息列
            topicLikeDao.delete(topic.getId(), 2);
            //删除话题
            topicsDao.delete(topic.getId());
        }
        //删除用户的点赞的话题的相关信息
        topicLikeDao.delete(along,1);
        //删除用户的点赞的评论的相关信息
        explainLikeDao.delete(along,1);
        //删除用户关注和用户被关注的信息
        attentionDao.customerDelete(along);
        //最后删除用户
        return customerDao.delete(along);
    }
    //修改用户信息
    public boolean modify(Customer customer,boolean is){
        if(is){
            String password=customer.getPassword();
            //获得新生成的盐值
            String salt=HavSal();
            customer.setSalt(salt);
            //对新密码进行加密
            customer.setPassword(encrypt(password,salt));
        }
        return customerDao.modify(customer);
    }
    //查询用户发表的评论
    public List<Explain> findExplain(Long along){
        return explainDao.findCus(along);
    }
    //查询用户发表的话题
    public List<Topic> findTopic(Long along){
        return topicsDao.findUser(along);
    }
}
