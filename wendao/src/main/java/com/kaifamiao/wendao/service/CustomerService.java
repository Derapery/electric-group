package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.CustomerDao;
import com.kaifamiao.wendao.dao.ExplainDao;
import com.kaifamiao.wendao.dao.TopicsDao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.List;

public class CustomerService {
    private CustomerDao customerDao;
    private ExplainDao explainDao;
    private TopicsDao topicsDao;
    //获得一个SnowflakeIdGenerator实例
    private SnowflakeIdGenerator snowId= SnowflakeIdGenerator.getInstance();
    public CustomerService(){
        customerDao=new CustomerDao();
        explainDao=new ExplainDao();
        topicsDao=new TopicsDao();
    }
    //加密码进行加密
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
        customer.setPassword(encrypt(pass, customer.getSalt()));
        return customerDao.save(customer);
    }
    //根据ID查找用户
    public Customer find(Long along){
        return customerDao.find(along);
    }
    //根据用户名来查找用户
    public Customer find(String username){
        return customerDao.findName(username);
    }
    //查询用户列表
    public List<Customer> findAll(){
        return customerDao.finaAll();
    }
    //删除用户
    public boolean delete(Long along){
        //先删除用户存储的评论
        explainDao.deleteCUS(along);
        //删除用户存储的话题
        List<Topic> list=topicsDao.findUser(along);
        for (Topic topic:list) {
            //删除话题的相关评论
            explainDao.deleteTop(topic.getId());
            //删除话题
            topicsDao.delete(topic.getId());
        }
        //最后删除用户
        return customerDao.delete(along);
    }
    //修改用户信息
    public boolean modify(Customer customer){
        String password=customer.getPassword();
        //获得新生成的盐值
        String salt=HavSal();
        customer.setSalt(salt);
        //对新密码进行加密
        customer.setPassword(encrypt(password,salt));
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
