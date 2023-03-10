package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.CustomerDao;
import com.kaifamiao.wendao.dao.ExplainDao;
import com.kaifamiao.wendao.dao.ExplainLikeDao;
import com.kaifamiao.wendao.dao.TopicsDao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.LikeExplain;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class ExplainService {
    private SnowflakeIdGenerator snowflakeIdGenerator;
    private ExplainDao explainDao;
    private ExplainLikeDao explainLikeDao;
    private TopicsDao topicsDao;
    private TopicService topicService;
    private CustomerDao customerDao;
    public ExplainService(){
        super();
        snowflakeIdGenerator=SnowflakeIdGenerator.getInstance();
        explainDao=new ExplainDao();
        explainDao=new ExplainDao();
        explainLikeDao=new ExplainLikeDao();
        topicsDao=new TopicsDao();
        topicService=new TopicService();
        customerDao=new CustomerDao();
    }
    //删除评论
    public boolean delete(Long explain_id){
            //删除话题相关的点赞信息
            explainLikeDao.delete(explain_id,2);
            //删除话题
            return explainDao.delete(explain_id);
    }
//保存评论
    public boolean save(Explain explain){
        LocalDateTime localDateTime=LocalDateTime.now();
        explain.setPublishTime(localDateTime);
        explain.setId(snowflakeIdGenerator.generate());
        return explainDao.save(explain);
    }
    //查找评论
    public Explain find(Long id){
        return explainDao.find(id);
    }
    //修改评论的点赞和踩的数量
    public boolean modify(Integer count,Long id,int tage){
        return explainDao.modify(count,id,tage);
    }
    //查看我的评论相对应的话题
    public List<Explain> explainMy(Long customer_id){
        //先按照用户ID查找评论
        List<Explain> explains = explainDao.findCus(customer_id);
        //遍历集合
        for(Explain explain:explains){
            Customer customer=customerDao.find(explain.getAuthor().getId());
            Topic topic= explain.getTopic();
            topic=topicService.searchOne(topic.getId(),customer.getId());
            explain.setAuthor(customer);
            explain.setTopic(topic);
        }
        return explains;
    }
}
