package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExplainDao extends BaseDao<Explain,Long> {
    private String INSERT_ONE="INSERT INTO t_explains(content,publish_time,publish_address,praise,despise,customer_id,topic_id,id) VALUES(?,?,?,?,?,?,?,?)";
    private String SELECT_MAX_ID="SELECT max(id) FROM t_explains";
    private String DELETE_BASE="DELETE FROM t_explains WHERE ";
    private String DELETE_ID=DELETE_BASE+" id=?";
    private String DELETE_CUS=DELETE_BASE+" customer_id=?";
    private String DELETE_TOP=DELETE_BASE+" topic_id=?";
    private String FIND_ONE_BASE="SELECT content,publish_time,publish_address,praise,despise,customer_id,topic_id,id FROM t_explains WHERE ";
    private String FIND_ONE_ID=FIND_ONE_BASE+" id=?";
    private String FIND_ONE_CUS=FIND_ONE_BASE+" customer_id=?";
    private String FIND_ONE_TOP=FIND_ONE_BASE+" topic_id=?";
    private String FIND_ALL="SELECT content,publish_time,publish_address,praise,despise,customer_id,topic_id,id FROM t_explains ORDER BY topic_id";
    private String MODIFY_PRAISE="UPDATE t_explains SET praise=? WHERE id=?  ";
    private String MODIFY_DESPISE="UPDATE t_explains SET despise=? WHERE id=?";
    private CustomerDao cusDAO=new CustomerDao();
    private TopicsDao topicsDao=new TopicsDao();
    private Explain raHand(ResultSet rs)throws SQLException{
        if(rs != null){
            Explain explain= new Explain();
            explain.setId(rs.getLong("id"));
            Customer customer=cusDAO.find(rs.getLong("customer_id"));
            explain.setAuthor(customer);
            explain.setPublishTime(rs.getTimestamp("publish_time").toLocalDateTime());
            explain.setContent(rs.getString("content"));
            explain.setPublishAddress(rs.getString("publish_address"));
            explain.setPraise(rs.getInt("praise"));
            explain.setDespise(rs.getInt("despise"));
            Topic topic=topicsDao.find(rs.getLong("topic_id"));
            explain.setTopic(topic);
         return explain;
        }
        return null;
    };
    //???List<explain>?????????????????????
    private ResultSetHandler<List<Explain>> handler=rs->{
        List<Explain> list=new ArrayList<>();
        while(rs.next()){
            Explain explain=raHand(rs);
            list.add(explain);
        }
        return list;
    };
    //????????????
    @Override
    public boolean save(Explain explain) {
        LocalDateTime dateTime=explain.getPublishTime();
        java.sql.Timestamp timestamp=java.sql.Timestamp.valueOf(dateTime);
        Customer customer=explain.getAuthor();
        Topic topic=explain.getTopic();
        Object[] objects={explain.getContent(),timestamp,explain.getPublishAddress(),explain.getPraise(),explain.getDespise(),
                customer.getId(),topic.getId(),explain.getId()};
        try {
            return runner.update(INSERT_ONE,objects)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("?????????????????????",cause);
        }
    }

    @Override
    public boolean modify(Explain explain) {
        return false;
    }
    //????????????????????????????????????
    public boolean modify(Integer count,Long id,int tage){
        try{
            Object[] objects={count,id};
            if(tage==1){
                return  runner.update(MODIFY_PRAISE,objects)==1;
            }else{
                return runner.update(MODIFY_DESPISE,objects)==1;
            }
        }catch (SQLException cause){
            throw new RuntimeException("????????????????????????????????????");
        }


    }
    //????????????
    @Override
    public boolean delete(Long aLong) {
        return deleteSame(DELETE_ID,aLong);
    }
    //????????????ID???????????????
    public boolean deleteCUS(Long aLong){
        return deleteSame(DELETE_CUS,aLong);
    }
    //???????????????????????????
    public boolean deleteTop(Long along){
        return deleteSame(DELETE_TOP,along);
    }
    //???????????????????????????????????????
    private boolean deleteSame(String sql,Long along){
        try {
            return runner.update(sql,along)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("????????????????????????",cause);
        }
    }
    //???????????????ID??????
    @Override
    public Explain find(Long aLong) {
        try {
            return runner.query(FIND_ONE_ID,rs->rs.next()?raHand(rs):null,aLong);
        } catch (SQLException cause) {
            throw new RuntimeException("????????????????????????",cause);
        }
    }
    //???????????????????????????
    @Override
    public List<Explain> finaAll() {
        return seek(FIND_ALL,null);
    }
    //????????????????????????????????????????????????
    private List<Explain> seek(String sql,Long along){
        try {
            if(along==null){
                return runner.query(sql,handler);
            }
            return runner.query(sql,handler,along);
        } catch (SQLException cause) {
            throw new RuntimeException("????????????????????????",cause);
        }
    }
    //????????????????????????
    public List<Explain> findCus(Long along){
        return seek(FIND_ONE_CUS,along);
    }
    //????????????????????????
    public  List<Explain> findTop(Long along){
        return seek(FIND_ONE_TOP,along);
    }
}
