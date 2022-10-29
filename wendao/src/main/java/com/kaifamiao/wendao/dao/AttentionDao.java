package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Category;
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
    private static final String FIND_BY_ATTENTION = "SELECT up_id , follwer_id FROM t_category WHERE follwer_id = ?";
    private static final String FIND_BY_FANS = "SELECT follwer_id , follwer_id FROM t_category WHERE up_id = ?";

    //添加关注的信息到表中
    @Override
    public boolean save(Attention attention) {
        try {
            return runner.update(INSERT_ONE,attention.getId(),attention.getAttention_id(),attention.getFans_id())==1;
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
    @Override
    public Attention find(Long aLong) {
        return null;
    }

    @Override
    public List<Attention> finaAll() {
        return null;
    }

    public List<Customer> findByCustomer(Customer customer) {
        ResultSetHandler<List<Customer>> rsHandler = rs -> {
            List<Customer> list = new ArrayList<>();
            if( rs.next() ) {
                Customer c = wrap(rs);
                list.add(c);
            }
            return list;
        };
        try {
            return runner.query(FIND_BY_ATTENTION,rsHandler,customer.getId());
        } catch (SQLException cause) {
            throw new RuntimeException("查询关注列表失败", cause);
        }
    }

    public List<Customer> findByFans(Customer customer) {
        ResultSetHandler<List<Customer>> rsHandler = rs -> {
            List<Customer> list = new ArrayList<>();
            if( rs.next() ) {
                Customer c = wrap(rs);
                list.add(c);
            }
            return list;
        };
        try {
            return runner.query(FIND_BY_FANS,rsHandler,customer.getId());
        } catch (SQLException cause) {
            throw new RuntimeException("查询粉丝列表失败", cause);
        }
    }

    private Customer wrap(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setId(rs.getLong("up_id"));
        return c;
    }

}
