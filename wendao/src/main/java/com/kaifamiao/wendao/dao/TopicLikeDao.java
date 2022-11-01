package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.Praise;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicLikeDao extends BaseDao<Praise,Long>{
    private static final String INSERT_ONE="INSERT INTO t_like(topic_id,customer_id,praise,id) VALUES(?,?,?,?)";
    private static  final String UPDATE="UPDATE t_like SET praise=? WHERE topic_id=? AND customer_id=?";
    private static final String DELETE="DELETE FROM t_like WHERE topic_id=? AND customer_id=?";
    private static final String FIND="SELECT topic_id,customer_id,praise,id FROM t_like WHERE  topic_id=? AND customer_id=?";
    private static final String SELECT_LIKE="SELECT topic_id FROM t_like WHERE praise=1 AND customer_id=?";
    private static final String UPCOUNT="SELECT count(1) FROM t_like WHERE praise=1 AND topic_id=?";
    private static final String DOWNCOUNT="SELECT count(1) FROM t_like WHERE praise=0 AND top_level_count=?";
    private static final String SELECT_CUS="SELECT topic_id,customer_id,praise,id WHERE customer_id=? ";
    private static final String SELECT_TOP="SELECT  topic_id,customer_id,praise,id WHERE topic_id=?";
    private static final String DELETE_CUS="DELETE FROM t_like WHERE customer_id=?";
    private static final String DELETE_TOP="DELETE FROM t_like WHERE topic_id=?";
    private Praise rsHander(ResultSet rs) throws SQLException {
        if(rs == null){
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
    }
    private List<Praise> rsList(ResultSet rs) throws SQLException {
        List<Praise> list = new ArrayList<>();
        while(rs.next()){
         Praise praise = rsHander(rs);
         list.add(praise);
         return  list;
        }
        return list;
    }
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
    public boolean delete(Long customer_id,Long topic_id){
        Object[]  objects={topic_id,customer_id};
        try {
            return runner.update(DELETE,objects)==1;
        } catch (SQLException e) {
            throw new RuntimeException("删除话题失败！",e);
        }
    }
    //根据用户的ID来删除用户信息
    public boolean delete(Long customer, Integer i ){
        try {
            if(i==1){
                return runner.update(DELETE_CUS,customer)==1;
            }else{
                return runner.update(DELETE_TOP,customer) ==1;
            }

        } catch (SQLException e) {
            throw new RuntimeException("根据用户删除话题失败！",e);
        }
    }


    @Override
    public Praise find(Long aLong) {
        return null;
    }
    //查找当前用户和当前话题对应的点赞或者踩的记录
    public Praise find(Long customer_id,Long topic_id){
        Object[]  objects={topic_id,customer_id};
        try {
            return runner.query(FIND,rs ->rs.next()?rsHander(rs):null,objects);
        } catch (SQLException e) {
            throw new RuntimeException("删除话题失败！",e);
        }
    }
    //根据传入的用户的ID来获取用户点赞的话题的信息
    public List<Topic> likeList(Long along){
        try {
            return runner.query(SELECT_LIKE,rsList,along);
        } catch (SQLException e) {
            throw new RuntimeException("获取点赞失败！",e);
        }
    }
    @Override
    public List<Praise> finaAll() {
        return null;
    }
    //查询话题的点赞的数量
    public Integer thumbUPCount(Long topic_id){
        try {
            return runner.query(UPCOUNT,rs ->rs.next()?rs.getInt(1):0,topic_id);
        } catch (SQLException e) {
            throw new RuntimeException("查询话题点赞数错误！",e);
        }
    }
    //查询话题的踩的数量
    public Integer thumbDownCount(Long topic_id){
        try {
           return runner.query(DOWNCOUNT,rs ->rs.next()?rs.getInt(1):0,topic_id);
        } catch (SQLException e) {
            throw new RuntimeException("查询话题踩数错误！",e);
        }
    }
    //查询信息记录的列表
    public List<Praise> findList(Long along,Integer i){
        try {
            if(i==1){
           return runner.query(SELECT_CUS,rs->rsList(rs),along);
            }else{
                return runner.query(SELECT_TOP,rs->rsList(rs),along);
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询所有相同话题的相关的点赞信息！",e);
        }
    }
}
