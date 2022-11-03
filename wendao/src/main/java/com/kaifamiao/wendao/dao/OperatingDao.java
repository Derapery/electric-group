package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Operating;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperatingDao extends BaseDao<Operating,Long>{

    private static final String INSERT_ONE="INSERT INTO operationing(id,handler_id,user_id,type,state) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE operationing SET state = ? where handler_id = ? and user_id = ?";
    private static final String FIND_ALL = "SELECT id,handler_id,user_id,type,state  FROM operationing ORDER BY id";
    private static final String FIND = "SELECT id,handler_id,user_id,type,state FROM operationing WHERE id = ?";

    @Override
    public boolean save(Operating operating) {
        try {
            return runner.update(INSERT_ONE,operating.getId(),operating.getHandler_id(),operating.getUser_id(),operating.getType(),operating.getState())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("添加失败！",cause);
        }
    }

    @Override
    public boolean modify(Operating operating) {
        try {
            return runner.update(UPDATE,operating.getState(),operating.getHandler_id(),operating.getUser_id())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("更新失败！",cause);
        }
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public Operating find(Long id) {
        ResultSetHandler<Operating> rsHandler = rs -> {
            if( rs.next() ) {
                Operating o = wrap(rs);
                return o;
            }
            return null;
        };
        try {
            return runner.query(FIND,rsHandler,id);
        } catch (SQLException cause) {
            throw new RuntimeException("删除失败", cause);
        }
    }

    private Operating wrap(ResultSet rs) throws SQLException {
        Operating o = new Operating();
        o.setId(rs.getLong("id"));
        o.setHandler_id(rs.getLong("handler_id"));
        o.setUser_id(rs.getLong("user_id"));
        o.setType(rs.getInt("type"));;
        o.setState(rs.getInt("state"));
        return o;
    }

    @Override
    public List<Operating> finaAll() {
        ResultSetHandler <List<Operating>> rsHandler = rs -> {
            List<Operating> list= new ArrayList<>();
            while( rs.next() ) {
                Operating o = wrap(rs);
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

}
