package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.utils.LikeExplain;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExplainLikeDao extends BaseDao<LikeExplain,Long>{
    private static final String INSERT="INSERT INTO t_like_explain(customer_id,explain_id,praise,id) VALUES(?,?,?,?)";
    private static final String DELETE="DELETE FROM t_like_explain WHERE customer_id=? AND explain_id=?";
    private static final String FIND="SELECT customer_id,explain_id,praise,id FROM t_like_explain WHERE customer_id=? AND explain_id=?";
    private  static final  String DELETE_CUS="DELETE FROM t_like_explain WHERE customer_id=?";
    private  static final String DELETE_EXPLAIN="DELETE FROM t_like_explain WHERE explain_id=?";
    private  static final String FIND_EXPLAIN="SELECT customer_id,explain_id,praise,id FROM t_like_explain WHERE  explain_id=? ";
    //结果集的处理
    private LikeExplain rsHander( ResultSet rs ) throws SQLException{
        LikeExplain likeExplain=new LikeExplain();
        likeExplain.setExplain_id(rs.getLong("explain_id"));
        likeExplain.setId(rs.getLong("id"));
        likeExplain.setCustomer_id(rs.getLong("customer_id"));
        likeExplain.setState(rs.getInt("praise"));
        return likeExplain;
    }
    //返回列表的结果集的处理
    private List<LikeExplain> Hander(ResultSet rs) throws  SQLException{
        List<LikeExplain> list = new ArrayList<>();
        while (rs.next()){
            LikeExplain likeExplain=rsHander(rs);
            list.add(likeExplain);
        }
        return list;
    }
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
            return runner.query(FIND,rs ->rs.next()?rsHander(rs):null,objects);
        } catch (SQLException e) {
            throw new RuntimeException("查询！",e);
        }
    }
    public List<LikeExplain> findExplain(Long explain_id){
        try {
           return runner.query(FIND_EXPLAIN,rs ->Hander(rs),explain_id);
        } catch (SQLException e) {
            throw new RuntimeException("根据评论ID删除记录失败！",e);
        }
    }

    @Override
    public List<LikeExplain> finaAll() {
        return null;
    }
}
