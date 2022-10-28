package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Customer;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends BaseDao<Customer,Long>{
    private String INSERT_ONE="INSERT INTO t_customer(username,password,salt,nickname,reg_date,id) VALUES(?,?,?,?,?,?)";
    private String SELECT_MAX_ID="SELECT MAX(id) FROM t_customer";
    private String Modify="UPDATE t_customer SET username=?,password=?,salt=?,nickname=? WHERE id=?";
    private String DELETE="DELETE FROM t_customer WHERE id=?";
    private String SELECT_ONE_BASE="SELECT username,password,salt,nickname,reg_date,id FROM t_customer WHERE";
    private String SELECT_ONE= SELECT_ONE_BASE+" id=?";
    private String SELECT_ONE_USERNAME=SELECT_ONE_BASE+" username=?";
    private String SELECT_ALL="SELECT username,password,salt,nickname,reg_date,id FROM t_customer ORDER BY id";
    @Override
    public boolean save(Customer customer) {
        LocalDate local=customer.getRegisterDate();
        java.sql.Date date=java.sql.Date.valueOf(local);
        Object [] objects={
                customer.getUsername(),customer.getPassword(),customer.getSalt(),customer.getNickname(),
                date,customer.getId()
        };
        try {
            return runner.update(INSERT_ONE,objects)==1;
        } catch (SQLException cause) {
           throw new RuntimeException("填入用户时失败！",cause);
        }
    }
    @Override
    public boolean modify(Customer customer) {
        Object [] objects={customer.getUsername(),customer.getPassword(),
                customer.getSalt(),customer.getNickname(),customer.getId()};
        try {
            return runner.update(Modify,objects)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("修改用户数据时发生错误！",cause);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        try {
            return runner.update(DELETE,aLong)==1;
        } catch (SQLException cause) {
            throw new RuntimeException("删除用户数据时发生错误！",cause);
        }
    }
    //根据ID来查询用户的信息
    @Override
    public Customer find(Long aLong) {
        return seek(SELECT_ONE,aLong);
    }
    //用用户名来查询用户的信息
    public Customer findName(String username){
        return seek(SELECT_ONE_USERNAME,username);
    }
    //将查询用户信息的表进行封装
    private Customer seek(String sql,Object tage){
        try {
            Customer customer=runner.query(sql,rs->rs.next()?rsHandler(rs):null,tage);
            return customer;
        } catch (SQLException cause) {
            throw new RuntimeException("查询客户信息失败！");
        }
    }
    //对结果集进行处理
    private Customer rsHandler(ResultSet rs) throws SQLException {
            if(rs !=null){
                Customer cus= new Customer();
                cus.setUsername(rs.getString("username"));
                cus.setPassword(rs.getString("password"));
                cus.setNickname(rs.getString("nickname"));
                cus.setId(rs.getLong("id"));
                cus.setSalt(rs.getString("salt"));
                java.sql.Date date= rs.getDate("reg_date");
                cus.setRegisterDate(date==null? null:date.toLocalDate());
                return cus;
            }
            return null;
    }
    @Override
    public List<Customer> finaAll() {
        ResultSetHandler<List<Customer>> resHand=rs->{
            List<Customer> list=new ArrayList<>();
            while(rs.next()){
                Customer customer=rsHandler(rs);
                list.add(customer);
            }
            return list;
        };
        try {
            return runner.query(SELECT_ALL,resHand);
        } catch (SQLException cause) {
            throw new RuntimeException("查询所有用户信息时发生错误！");
        }
    }
}
