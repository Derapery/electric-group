package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.*;
import com.kaifamiao.wendao.entity.*;
import com.kaifamiao.wendao.utils.Constants;
import com.kaifamiao.wendao.utils.LikeExplain;
import com.kaifamiao.wendao.utils.Paging;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.util.List;

public class ManagerService {

    private CustomerDao customerDao;
    private CategoryDao categoryDao;
    private ExplainDao explainDao;
    private TopicsDao topicsDao;
    private TopicLikeDao topicLikeDao;
    private ExplainLikeDao explainLikeDao;
    private OperatingDao operatingDao;
    private BadLogDao badLogDao;
    private SnowflakeIdGenerator snowId= SnowflakeIdGenerator.getInstance();

    public ManagerService(){
        customerDao=new CustomerDao();
        explainDao=new ExplainDao();
        topicsDao=new TopicsDao();
        topicLikeDao = new TopicLikeDao();
        categoryDao = new CategoryDao();
        explainLikeDao = new ExplainLikeDao();
        badLogDao = new BadLogDao();
        operatingDao = new OperatingDao();
    }


    public List<Customer> findAllCustomer() {
        return customerDao.finaAll();
    }

    public <T> Paging<T>  findPage(Integer size, Integer current, Integer page, Integer type){
        Paging<T> paging=new Paging<>();
        List<T> list= (List<T>) findAllCustomer();
        if (type== Constants.TYPE_TOPIC.getValue()){
             list= (List<T>)findAllTopic();
        }
        if (type== Constants.TYPE_BADLOG.getValue()){
            list= (List<T>)findAllBadLog();
        }
        if (type== Constants.TYPE_OPERATING.getValue()){
            list= (List<T>)findAllOperating();
        }
        Integer begin=(current-1)*size;
        Integer count = list.size();
        Integer total=count/size;
        total= count%size==0? total:total+1;
        paging.setSize(size);
        paging.setCurrent(current);
        paging.setTotal(total);
        paging.setBegin(begin);
        paging.setDataList(list);
        return paging;
    }

    private List<Operating> findAllOperating() {
        return  operatingDao.finaAll();
    }

    private List<BadLog> findAllBadLog() {
        return badLogDao.finaAll();
    }

    private List<Topic> findAllTopic() {
        List<Topic> topicList =  topicsDao.finaAll();
        for (Topic topic:topicList) {
            topic.setCategory_name(categoryDao.find(topic.getCategory_id()).getName());
            topic.setAuthor(customerDao.find(topic.getAuthor().getId()));
            topic.setExplains(explainDao.findTop(topic.getId()));
            topic.setThumbUpCount(topicLikeDao.thumbUPCount(topic.getId()));
            topic.setThumbDownCount(topicLikeDao.thumbDownCount(topic.getId()));
            if(topic.getTitle().length()>10){
                topic.setTitle(topic.getTitle().substring(0,7)+"...");
            }
        }
        return topicList;
    }

    public Category findCategory(Long id) {
        return categoryDao.find(id);
    }


    public boolean changeCategory(String category_id, String category_name) {
        Category category = new Category();
        category.setId(Long.parseLong(category_id));
        category.setName(category_name);
        return categoryDao.modify(category);
    }

    public Topic FindTopic(Long topic_id) {
        return topicsDao.find(topic_id);
    }

    public Customer findCustomer(String name) {
        return customerDao.findName(name);
    }
    public Customer findCustomerById(Long id) {
        return customerDao.find(id);
    }

    public boolean editCustomer(Customer customer) {
        return customerDao.modify(customer);
    }

    public Topic findTopic(Long topic_id) {
        return topicsDao.find(topic_id);
    }

    public boolean editTopic(Topic topic) {
        //调用DAO层的方法进行保存
        return topicsDao.modify(topic);
    }

    public List<Category> loadCategoryList() {
        return categoryDao.finaAll();
    }

    public Category loadCategory(Long id) {
        return categoryDao.find(id);
    }

    public boolean deleteTopic(Long id, Long topic_id) {
        List<Explain> explainList = explainDao.findTop(topic_id);
        //删除话题的评论及相关的点赞信息
        for (Explain explain : explainList) {
            LikeExplain explainLike = new LikeExplain();
            explainLike.setExplain_id(explain.getId());
            //删除每个评论的点赞信息
            explainLikeDao.delete(explain.getId(),2);
            //删除评论
            explainDao.delete(explain.getId());
        }
        //获取每个话题的点赞信息列
        topicLikeDao.delete(topic_id, 2);
        //删除话题
        return topicsDao.delete(topic_id);
    }

    public boolean changePwd(Customer customer) {
        return false;
    }

    public Paging<BadLog> findPageByOne(Integer size, Integer current, Integer page, Long id) {
        Paging<BadLog> paging=new Paging<>();
        List<BadLog> list= findBadlogById(id);
        Integer begin=(current-1)*size;
        Integer count = list.size();
        Integer total=count/size;
        total= count%size==0? total:total+1;
        paging.setSize(size);
        paging.setCurrent(current);
        paging.setTotal(total);
        paging.setBegin(begin);
        paging.setDataList(list);
        return paging;
    }

    private List<BadLog> findBadlogById(Long id) {
        return badLogDao.findById(id);
    }


    public boolean changeManagement(Long customer_id, Long management) {
        return customerDao.modifyLevel(customer_id,management);
    }

    public void addOperating(Long handler_id,Long user_id,Integer state,Integer type) {
        Operating op = new Operating();
        op.setId(snowId.generate());
        op.setHandler_id(handler_id);
        op.setUser_id(user_id);
        op.setType(type);
        op.setState(state);
        operatingDao.save(op);
    }


    public List<Category> findAllCategory() {
        return categoryDao.finaAll();
    }

    public boolean addCategory(String category_name) {
        Category category = new Category();
        category.setId(snowId.generate());
        category.setName(category_name);
        return categoryDao.save(category);
    }
}
