package com.kaifamiao.wendao.dao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.utils.Attention;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttentionDao extends BaseDao<Attention,Long>{

    private static final String INSERT_ONE="INSERT INTO  t_fans(id,follwer_id,up_id) VALUES(?,?,?)";
    private static final String DELETE = "DELETE FROM t_fans WHERE follwer_id = ? and up_id = ?";
    private static final String FIND_BY_ATTENTION = "SELECT up_id FROM t_fans WHERE follwer_id = ?";
    private static final String FIND_BY_FANS = "SELECT  follwer_id FROM t_fans WHERE up_id = ?";
    private static final String DELETE_CUSTOMER="DELETE FROM t_fans WHERE follwer_id=? OR up_id=?";
    private static final String SELECT_FOCUS="SELECT id,follwer_id,up_id FROM t_fans  WHERE follwer_id=? AND up_id=?";
    private static final String DELETE_FANS="DELETE FROM t_fans WHERE follwer_id=? AND up_id=?";
    private static final String DELETE_ATTEN="DELETE FROM t_fans WHERE up_id=? AND follwer_id=?";
    private CustomerDao customerDao=new CustomerDao();
    private   ResultSetHandler<List<Customer>> rsHandler = rs -> {
        List<Customer> list = new ArrayList<>();
        while( rs.next() ) {
            Customer c = wrap(rs);
            list.add(c);
        }
        return list;
    };
    private Customer wrap(ResultSet resultSet) throws SQLException {
        Customer customer = customerDao.find(resultSet.getLong(1));
        return customer;
    }
    //根据传入的关注着和被关注着的信息获取Attention
    private Attention rsHand(ResultSet rs) throws SQLException {
        if(rs != null){
            Attention attention=new Attention();
            //粉丝的ID
            attention.setFans_id(rs.getLong("follwer_id"));
            attention.setAttention_id(rs.getLong("up_id"));
            attention.setId(rs.getLong("id"));
            return attention;
        }
        return null;
    }
    //添加关注的信息到表中
    @Override
    public boolean save(Attention attention) {
        try {
            return runner.update(INSERT_ONE,attention.getId(),attention.getFans_id(),attention.getAttention_id())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("关注信息保存失败！",cause);
        }
    }

    @Override
    public boolean modify(Attention attention) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
    //删除用户的关注的信息
    public boolean delete(Attention attention) {
        try {
            return runner.update(DELETE,attention.getFans_id(),attention.getAttention_id()) == 1 ;
        } catch (SQLException cause) {
            throw new RuntimeException("删除失败", cause);
        }
    }
    //删除关注信息
    public boolean delete(Long fans,Long attention,int tage){
        try{
            Object [] objects={fans,attention};
            if(tage==1){
                return runner.update(DELETE_FANS,objects)==1;
            }else{
                return runner.update(DELETE_ATTEN,objects)==1;
            }
        }catch (SQLException cause){
            throw  new RuntimeException("删除关注信息失败！",cause);
        }

    }
    //删除关注用户的信息
    public void customerDelete(Long along){
        try{
            runner.update(DELETE_CUSTOMER,along,along);
        }catch (SQLException cause){
            throw new RuntimeException("删除用户相关关注信息失败!",cause);
        }
    }
    @Override
    public Attention find(Long aLong) {
        return null;
    }

    @Override
    public List<Attention> finaAll() {
        return null;
    }
    //关注用户列表
    public List<Customer> findByCustomer(Customer customer) {
        try {
            return runner.query(FIND_BY_ATTENTION,rsHandler,customer.getId());
        } catch (SQLException cause) {
            throw new RuntimeException("查询关注列表失败", cause);
        }
    }
    //自己的粉丝的列表
    public List<Customer> findByFans(Customer customer) {
        try {
            return runner.query(FIND_BY_FANS,rsHandler,customer.getId());
        } catch (SQLException cause) {
            throw new RuntimeException("查询粉丝列表失败", cause);
        }
    }
    //查找关注的信息
    public Attention findSecond(Long fans,Long attents)  {
        Object[] objects = {fans,attents};
        try {
            return runner.query(SELECT_FOCUS,rs ->rs.next()? rsHand(rs):null,objects);
        } catch (SQLException cause) {
            throw new RuntimeException("查询点赞信息时发生错误!",cause);
        }
    }

}
