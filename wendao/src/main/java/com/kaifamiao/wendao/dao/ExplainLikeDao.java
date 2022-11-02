package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.utils.LikeExplain;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;
import java.util.List;

public class ExplainLikeDao extends BaseDao<LikeExplain,Long>{
    private static final String INSERT="INSERT INTO t_like_explain(customer_id,explain_id,praise,id) VALUES(?,?,?,?)";
    private static final String DELETE="DELETE FROM t_like_explain WHERE customer_id=? AND explain_id=?";
    private static final String FIND="SELECT customer_id,explain_id,praise,id FROM t_like_explain WHERE customer_id=? AND explain_id=?";
    private  static final  String DELETE_CUS="DELETE FROM t_like_explain WHERE customer_id=?";
    private  static final String DELETE_EXPLAIN="DELETE FROM t_like_explain WHERE topic_id=?";
    //结果集的处理
    private ResultSetHandler<LikeExplain> rsHander=rs ->{
        if(rs.next()){
            LikeExplain likeExplain=new LikeExplain();
            likeExplain.setExplain_id(rs.getLong("explain_id"));
            likeExplain.setId(rs.getLong("id"));
            likeExplain.setCustomer_id(rs.getLong("customer_id"));
            likeExplain.setState(rs.getInt("praise"));
            return likeExplain;
        }
        return null;
    };
    //添加评论点赞信息
    @Override
    public boolean save(LikeExplain likeExplain) {
        Long customer_id=likeExplain.getCustomer_id();
        Long explain_id=likeExplain.getExplain_id();
        Integer state=likeExplain.getState();
        Long id=likeExplain.getId();
        Object [] objects={ customer_id,explain_id,state,id};
        try {
            return runner.update(INSERT,objects)==1;
        } catch (SQLException e) {
            throw new RuntimeException("评论点赞功能信息添加失败！",e);
        }
    }

    @Override
    public boolean modify(LikeExplain likeExplain) {
        return false;
    }
    //删除表中用户对评论的点赞信息
    @Override
    public boolean delete(Long aLong) {
        return false;
    }
    public boolean delete(Long customer_id,Long explain_id){
        Object [] objects={ customer_id,explain_id};
        try {
            return runner.update(DELETE,objects)==1;
        } catch (SQLException e) {
            throw new RuntimeException("删除点赞信息是发生错误！",e);
        }
    }
    //根据用户删除点赞信息是发生错误
    public boolean delete(Long along ,Integer i){
        try {
            if(i==1) {
                return runner.update(DELETE_CUS, along) == 1;
            }else{
                return runner.update(DELETE_EXPLAIN,along) ==1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("根据用户删除点赞信息是发生错误！",e);
        }
    }

    @Override
    public LikeExplain find(Long aLong) {
        return null;
    }
    //查询评论的点赞信息
    public LikeExplain find(Long customer_id,Long explain_id){
        Object [] objects={ customer_id,explain_id};
        try {
            return runner.query(FIND,rsHander,objects);
        } catch (SQLException e) {
            throw new RuntimeException("查询！",e);
        }
    }

    @Override
    public List<LikeExplain> finaAll() {
        return null;
    }
}
