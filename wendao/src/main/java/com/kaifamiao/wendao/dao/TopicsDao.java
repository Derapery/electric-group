package com.kaifamiao.wendao.dao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Topic;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TopicsDao extends BaseDao<Topic,Long> {
    private CustomerDao cusDao=new CustomerDao();
    private String INSERT_ONE="INSERT INTO t_topics(title,content,publish_time,publish_address,priority,customer_id,id,category_id) VALUES(?,?,?,?,?,?,?,?)";
    private String SELECT_MAX_ID="SELECT max(id) FROM t_topics";
    private String MODIFY="UPDATE t_topics SET title=?,content=?,publish_time=?,publish_address=?,priority=?,customer_id=? WHERE id=?";
    private String DELETE_ONE="DELETE FROM t_topics WHERE id=?";
    private String FIND_BASE="SELECT title,content,publish_time,publish_address,priority,customer_id,category_id,id FROM t_topics WHERE ";
    private String FIND_ONE_ID=FIND_BASE+" id=?";
    private String FIND_USER_ID=FIND_BASE+" customer_id=?";
    private String FIND_ALL="SELECT title,content,publish_time,publish_address,priority,customer_id,category_id,id FROM t_topics";
    private String SELECT_COUNT_BASE="SELECT count(1) FROM t_topics";
    private String SELECT_COUNT_CUS=SELECT_COUNT_BASE+" WHERE customer_id=?";
    private String SELECT_PAGE_BASE=FIND_ALL+" ORDER BY id DESC LIMIT ?,?";
    private String SELECT_PAGE_CUS=FIND_ALL+" WHERE customer_id = ? ORDER BY id DESC LIMIT ?,?";
    private String SELECT_COUNT_LIKE=SELECT_COUNT_BASE+" WHERE title like ?";
    private static final String ADD_COUNT = "UPDATE t_topics SET priority=? WHERE id =?";
    private ResultSetHandler<List<Topic>> rsHand=rs->{
        List<Topic> list=new ArrayList<>();
        while(rs.next()){
            Topic topic=rsHandler(rs);
            list.add(topic);
        }
        return list;
    };
    //插入一条话题
    @Override
    public boolean save(Topic topic) {
        return storage(topic,INSERT_ONE);
    }
    //将数据封装，减少代码冗余
    private boolean  storage(Topic topic,String sql){
        Customer customer=topic.getAuthor();
        LocalDateTime date=topic.getPublishTime();
        java.sql.Timestamp dateTime =java.sql.Timestamp.valueOf(date);
        Object [] objects={topic.getTitle(),topic.getContent(),
                dateTime,topic.getPublishAddress(),topic.getPriority(),customer.getId(),topic.getId(),topic.getCategory_id()};
        try {
            return runner.update(sql,objects)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("保存或修改话题失败！",cause);
        }
    }
    //修改话题
    @Override
    public boolean modify(Topic topic) {
       return storage(topic,MODIFY);
    }
    //删除话题
    @Override
    public boolean delete(Long aLong) {
        try {
            return runner.update(DELETE_ONE,aLong)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("删除话题失败！",cause);
        }
    }

    //对于数据集的处理
    private Topic rsHandler(ResultSet rs) throws SQLException {
        if(rs!=null){
            Topic topic=new Topic();
            topic.setTitle(rs.getString("title"));
            topic.setContent(rs.getString("content"));
            topic.setPublishTime(rs.getTimestamp("publish_time").toLocalDateTime());
            topic.setPublishAddress(rs.getString("publish_address"));
            topic.setPriority(rs.getInt("priority"));
            topic.setId(rs.getLong("id"));
            Long customer_id=rs.getLong("customer_id");
            topic.setCategory_id(rs.getLong("category_id"));
            Customer customer= cusDao.find(customer_id);
            topic.setAuthor(customer);
            return topic;
        }
        return null;
    }
    //查找话题
    @Override
    public Topic find(Long aLong) {
        try {
             return runner.query(FIND_ONE_ID,rs->rs.next()?rsHandler(rs):null,aLong);
        } catch (SQLException cause ) {
            throw new RuntimeException("查询话题失败！",cause);
        }
    }

    //根据用户的ID来查询话题列表
    public List<Topic> findUser(Long along){
       return seekAll(FIND_USER_ID,along);
    }
    //查询所有的话题封装
    private List<Topic> seekAll(String sql,Long tage){
        try {
            if(tage==null){
                return runner.query(sql,rsHand);
            }
            return runner.query(sql,rsHand,tage);
        } catch (SQLException cause) {
            throw new RuntimeException("查询话题列表失败！",cause);
        }
    }

    //查询所有的话题
    @Override
    public List<Topic> finaAll() {
        return seekAll(FIND_ALL,null);
    }
    //查询话题的数目
    public Integer findCount(Long along){
        try{
            if(along==null){
                return runner.query(SELECT_COUNT_BASE,rs -> rs.next() ? rs.getInt(1) : 0);
            }else{
                return runner.query(SELECT_COUNT_CUS,rs -> rs.next()? rs.getInt(1):0,along);
            }
        }catch (SQLException cause){
            throw new RuntimeException("查询数据条目时发生错误！",cause);
        }
    }

    //根据分页查询话题
    public List<Topic> findPage(Integer begin,Integer size,Long customer_id ,Integer top){
        try{
            if (top==1) {
                Object[] parms={begin,size};
                return runner.query(SELECT_PAGE_BASE,rsHand,parms);
            }else{
                Object[] objects={customer_id,begin,size};
                return runner.query(SELECT_PAGE_CUS,rsHand,objects);
            }
        }catch (SQLException cause){
            throw new RuntimeException("根据分页查询话题失败！",cause);
        }
    }

    //根据用户搜索的关键字进行模糊查询话题的数
    public Integer findCountLike(String keyWord){
        try{
            return runner.query(SELECT_COUNT_LIKE,rs -> rs.next()?rs.getInt(1):0,"%"+keyWord+"%");
        }catch (SQLException cause){
            throw  new RuntimeException("模糊查询话题数时失败！",cause);
        }
    }

    //根据用户搜索的关键字进行模糊查询
    public List<Topic> findPageLike(Integer begin,Integer size,String keyWord){
        try{
            return runner.query(FIND_ALL+" WHERE title like ? ORDER BY id DESC LIMIT ?,?",rsHand,"%"+keyWord+"%",begin,size);
        }catch (SQLException cause){
            throw new RuntimeException("进行话题模糊查询时发生错误！",cause);
        }
    }

    public void addcount(Long id){
        try{
            Topic t = this.find(id);
            Integer priority = t.getPriority();
            runner.update(ADD_COUNT,priority+1,t.getId());
        }catch (SQLException cause){
            throw new RuntimeException("热度自增出现错误！",cause);
        }
    }

}
