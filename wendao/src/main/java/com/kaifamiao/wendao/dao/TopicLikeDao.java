package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.Praise;
import org.apache.commons.dbutils.ResultSetHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicLikeDao extends BaseDao<Praise,Long>{
    private static final String INSERT_ONE="INSERT INTO t_like(topic_id,customer_id,praise,id) VALUES(?,?,?,?)";
    private static  final String UPDATE="UPDATE t_like SET praise=? WHERE topic_id=? AND customer_id=?";
    private static final String DELETE="DELETE FROM t_like WHERE topic_id=? AND customer_id=?";
    private static final String FIND="SELECT topic_id,customer_id,praise,id FROM t_like WHERE  topic_id=? AND customer_id=?";
    private static final String SELECT_LIKE="SELECT topic_id FROM t_like WHERE praise=1 AND customer_id=?";
    private ResultSetHandler<Praise> rsHander=rs ->{
        if(rs.next()){
            Praise praise = new Praise();
            praise.setId(rs.getLong("id"));
            Topic topic=new Topic();
            topic.setId(rs.getLong("topic_id"));
            praise.setTopic(topic);
            Customer customer=new Customer();
            customer.setId(rs.getLong("customer_id"));
            praise.setCustomer(customer);
            praise.setState(rs.getInt("praise"));
            return praise;
        }
        return null;
    };
    private ResultSetHandler<List<Topic>> rsList= rs ->{
        List<Topic> list=new ArrayList<>();
        while (rs.next()){
            Topic topic=new Topic();
            topic.setId(rs.getLong(1));
            list.add(topic);
        }
        return list;
    };
    //保存一条点在或者踩的信息
    @Override
    public boolean save(Praise praise) {
        Topic topic =praise.getTopic();
        Customer customer=praise.getCustomer();
        Object [] objects={topic.getId(),customer.getId(),praise.getState(),praise.getId()};
        try {
           return runner.update(INSERT_ONE,objects)==1;
        } catch (SQLException e) {
            throw new RuntimeException("添加话题点赞信息失败！", e);
        }
    }
    //修改信息（此方法可能后期功能不会使用）
    @Override
    public boolean modify(Praise praise) {
        Topic topic =praise.getTopic();
        Customer customer=praise.getCustomer();
        Object [] objects={praise.getState(),topic.getId(),customer.getId()};
        try {
            return runner.update(UPDATE,objects)==1;
        } catch (SQLException e) {
            throw new RuntimeException("添加话题点赞信息失败！",e);
        }

    }
    @Override
    public boolean delete(Long aLong) {
        return false;
    }
    //删除一条点赞或者踩的消息
    public boolean delete(Praise praise){
        Topic topic=praise.getTopic();
        Customer customer=praise.getCustomer();
        Object[]  objects={topic.getId(),customer.getId()};
        try {
            return runner.update(DELETE,objects)==1;
        } catch (SQLException e) {
            throw new RuntimeException("删除话题失败！",e);
        }
    }

    @Override
    public Praise find(Long aLong) {
        return null;
    }
    //查找当前用户和当前话题对应的点赞或者踩的记录
    public Praise find(Praise praise){
        Topic topic=praise.getTopic();
        Customer customer=praise.getCustomer();
        Object[]  objects={topic.getId(),customer.getId()};
        try {
            return runner.query(FIND,rsHander,objects);
        } catch (SQLException e) {
            throw new RuntimeException("删除话题失败！",e);
        }
    }
    //根据传入的用户的ID来获取用户点赞的话题的信息
    public List<Topic> likeList(Long along){
        try {
            return runner.query(SELECT_LIKE,rsList,along);
        } catch (SQLException e) {
            throw new RuntimeException("删除话题失败！",e);
        }
    }
    @Override
    public List<Praise> finaAll() {
        return null;
    }
}
