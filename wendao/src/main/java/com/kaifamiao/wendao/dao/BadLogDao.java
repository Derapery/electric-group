package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.BadLog;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BadLogDao extends BaseDao<BadLog,Long>{

    private static final String INSERT_ONE="INSERT INTO bad_log (id,user_id,type) VALUES(?,?,?)";
    private static final String FIND_ALL = "SELECT id,user_id,type FROM bad_log ORDER BY id";
    private static final String FIND = "SELECT id,user_id,type FROM bad_log WHERE user_id = ?";


    @Override
    public boolean save(BadLog badLog) {
        try {
            return runner.update(INSERT_ONE,badLog.getId(),badLog.getUser_id(),badLog.getType())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("添加失败！",cause);
        }
    }

    @Override
    public boolean modify(BadLog badLog) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public BadLog find(Long user_id) {
        return null;
    }

    private BadLog wrap(ResultSet rs) throws SQLException {
        BadLog b = new BadLog();
        b.setId(rs.getLong("id"));
        b.setUser_id(rs.getLong("user_id"));
        b.setType(rs.getInt("type"));
        return b;
    }

    @Override
    public List<BadLog> finaAll() {
        ResultSetHandler <List<BadLog>> rsHandler = rs -> {
            List<BadLog> list= new ArrayList<>();
            while( rs.next() ) {
                BadLog o = wrap(rs);
                list.add(o);
            }
            return list;
        };
        try {
            return runner.query(FIND_ALL,rsHandler);
        } catch (SQLException cause) {
            throw new RuntimeException("查找记录失败！",cause);
        }
    }

    public List<BadLog> findById(Long user_id) {
        ResultSetHandler<List<BadLog>> rsHandler = rs -> {
            List<BadLog> list = new ArrayList<>();
            while( rs.next() ) {
                BadLog badLog = wrap(rs);
                list.add(badLog);
            }
            return list;
        };
        try {
            return runner.query(FIND,rsHandler,user_id);
        } catch (SQLException cause) {
            throw new RuntimeException("删除失败", cause);
        }
    }
}
