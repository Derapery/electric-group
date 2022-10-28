package com.kaifamiao.daotest;

import com.kaifamiao.wendao.dao.CustomerDao;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.utils.DataSourceFactory;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

public class CustomerDaoTest {
    private static CustomerDao dao;
    @BeforeClass
    public  static void setUp(){
        DataSourceFactory factory = DataSourceFactory.getInstance();
        factory.init();
        dao = new CustomerDao();
    }
    @Test
    public void saveTest(){
        Customer customer=new Customer();
        customer.setUsername("king");
        customer.setSalt("ASBE2");
        customer.setNickname("emperor");
        customer.setPassword("123456789");
        customer.setRegisterDate(LocalDate.now());
        boolean save=dao.save(customer);
        Assert.assertTrue(save);
    }
    @Test
    public void modifyTest(){
        Customer customer=new Customer();
        customer.setUsername("KING");
        customer.setSalt("622bs");
        customer.setNickname("emperor");
        customer.setPassword("123456789");
        customer.setId(1L);
        customer.setRegisterDate(LocalDate.now());
        boolean save=dao.modify(customer);
        Assert.assertTrue(save);
    }
}
