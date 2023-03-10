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
    private static String INSERT_ONE="INSERT INTO t_topics(id,title,content,publish_time,publish_address,priority,customer_id,category_id) VALUES(?,?,?,?,?,?,?,?)";
    private static String SELECT_MAX_ID="SELECT max(id) FROM t_topics";
    private static String MODIFY_EDIT="UPDATE t_topics SET title=?,content=? WHERE id=?";
    private static String MODIFY="UPDATE t_topics SET title=?,content=?,publish_time=?,publish_address=?,priority=?,customer_id=? WHERE id=?";
    private static String DELETE_ONE="DELETE FROM t_topics WHERE id=?";
    private static String FIND_BASE="SELECT title,content,publish_time,publish_address,priority,customer_id,category_id,id FROM t_topics WHERE ";
    private static String FIND_ONE_ID=FIND_BASE+" id=?";
    private static String FIND_USER_ID=FIND_BASE+" customer_id=?";
    private static String FIND_ALL="SELECT title,content,publish_time,publish_address,priority,customer_id,category_id,id FROM t_topics";
    private static String SELECT_COUNT_BASE="SELECT count(1) FROM t_topics";
    private static String SELECT_COUNT_CUS=SELECT_COUNT_BASE+" WHERE customer_id=?";
    private static String SELECT_PAGE_BASE=FIND_ALL+" ORDER BY id DESC LIMIT ?,?";
    private static String SELECT_PAGE_CUS=FIND_ALL+" WHERE customer_id = ? ORDER BY id DESC LIMIT ?,?";
    private static String SELECT_COUNT_LIKE=SELECT_COUNT_BASE+" WHERE title like ?";
    private static final String ADD_COUNT = "UPDATE t_topics SET priority=? WHERE id =?";
    private static final  String SEARCH_COUNT="SELECT count(1) FROM t_topics WHERE category_id=?";
    private static final String SEARCH=FIND_BASE+" category_id=? LIMIT ?,?";
    private ResultSetHandler<List<Topic>> rsHand=rs->{
        List<Topic> list=new ArrayList<>();
        while(rs.next()){
            Topic topic=rsHandler(rs);
            list.add(topic);
        }
        return list;
    };
    //??????????????????
    @Override
    public boolean save(Topic topic) {
        System.out.println(topic);
        return storage(topic,INSERT_ONE);
    }
    //????????????????????????????????????
    private boolean  storage(Topic topic,String sql){
        Customer customer=topic.getAuthor();
        LocalDateTime date=topic.getPublishTime();
        java.sql.Timestamp dateTime =java.sql.Timestamp.valueOf(date);
        Object [] objects={topic.getId(),topic.getTitle(),topic.getContent(),
                dateTime,topic.getPublishAddress(),topic.getPriority(),customer.getId(),topic.getCategory_id()};
        try {
            return runner.update(sql,objects)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("??????????????????????????????",cause);
        }
    }
    //????????????
    @Override
    public boolean modify(Topic topic) {
       return storage(topic,MODIFY);
    }
    //????????????
    @Override
    public boolean delete(Long aLong) {
        try {
            return runner.update(DELETE_ONE,aLong)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("?????????????????????",cause);
        }
    }

    //????????????????????????
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
    //????????????
    @Override
    public Topic find(Long aLong) {
        try {
             return runner.query(FIND_ONE_ID,rs->rs.next()?rsHandler(rs):null,aLong);
        } catch (SQLException cause ) {
            throw new RuntimeException("?????????????????????",cause);
        }
    }

    //???????????????ID?????????????????????
    public List<Topic> findUser(Long along){
       return seekAll(FIND_USER_ID,along);
    }
    //???????????????????????????
    private List<Topic> seekAll(String sql,Long tage){
        try {
            if(tage==null){
                return runner.query(sql,rsHand);
            }
            return runner.query(sql,rsHand,tage);
        } catch (SQLException cause) {
            throw new RuntimeException("???????????????????????????",cause);
        }
    }

    //?????????????????????
    @Override
    public List<Topic> finaAll() {
        return seekAll(FIND_ALL,null);
    }
    //?????????????????????
    public Integer findCount(Long along){
        try{
            if(along==null){
                return runner.query(SELECT_COUNT_BASE,rs -> rs.next() ? rs.getInt(1) : 0);
            }else{
                return runner.query(SELECT_COUNT_CUS,rs -> rs.next()? rs.getInt(1):0,along);
            }
        }catch (SQLException cause){
            throw new RuntimeException("????????????????????????????????????",cause);
        }
    }

    //????????????????????????
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
            throw new RuntimeException("?????????????????????????????????",cause);
        }
    }

    //????????????????????????????????????????????????????????????
    public Integer findCountLike(String keyWord){
        try{
            return runner.query(SELECT_COUNT_LIKE,rs -> rs.next()?rs.getInt(1):0,"%"+keyWord+"%");
        }catch (SQLException cause){
            throw  new RuntimeException("?????????????????????????????????",cause);
        }
    }

    //????????????????????????????????????????????????
    public List<Topic> findPageLike(Integer begin,Integer size,String keyWord){
        try{
            return runner.query(FIND_ALL+" WHERE title like ? ORDER BY id DESC LIMIT ?,?",rsHand,"%"+keyWord+"%",begin,size);
        }catch (SQLException cause){
            throw new RuntimeException("??????????????????????????????????????????",cause);
        }
    }

    public void addcount(Long id){
        try{
            Topic t = this.find(id);
            Integer priority = t.getPriority();
            runner.update(ADD_COUNT,priority+1,t.getId());
        }catch (SQLException cause){
            throw new RuntimeException("???????????????????????????",cause);
        }
    }

    public Integer searchCount(Long cate){
        try {
            return runner.query(SEARCH_COUNT,rs->rs.next()?rs.getInt(1):0,cate);
        } catch (SQLException cause) {
            throw  new RuntimeException("????????????????????????????????????",cause);
        }
    }
    public List<Topic> searchPage(Long cate,Integer begin,Integer size) {
        Object [] objects={cate,begin,size};
        try {
            return runner.query(SEARCH,rsHand,objects);
        } catch (SQLException cause) {
           throw  new RuntimeException("?????????????????????",cause);
        }
    }
    public boolean edit(Topic topic){
        try {
            return runner.update(MODIFY_EDIT,topic.getTitle(),topic.getContent(),topic.getId())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("?????????????????????",cause);
        }
    }

}
