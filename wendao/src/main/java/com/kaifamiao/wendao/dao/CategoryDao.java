package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Category;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends BaseDao<Category,Long>{

    private static final String INSERT_ONE="INSERT INTO t_category(id,name) VALUES(?,?)";
    private static final String UPDATE = "UPDATE t_category SET name = ? where id = ?";
    private static final String DELETE = "DELETE FROM t_category WHERE id = ?";
    private static final String FIND_ALL = "SELECT id,name FROM t_category ORDER BY id";
    private static final String FIND = "SELECT id,name FROM t_category WHERE id = ?";


    @Override
    public boolean save(Category category) {
        try {
            return runner.update(INSERT_ONE,category.getId(),category.getName())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("存储分类失败！",cause);
        }
    }

    @Override
    public boolean modify(Category category) {
        try {
            return runner.update(UPDATE,category.getName(),category.getId())==1;
        } catch (SQLException cause) {
            throw new RuntimeException("存储分类失败！",cause);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            return runner.update(DELETE,id) == 1 ;
        } catch (SQLException cause) {
            throw new RuntimeException("删除失败", cause);
        }
    }

    @Override
    public Category find(Long id) {
        ResultSetHandler<Category> rsHandler = rs -> {
            if( rs.next() ) {
                Category c = wrap(rs);
                return c;
            }
            return null;
        };
        try {
            return runner.query(FIND,rsHandler,id);
        } catch (SQLException cause) {
            throw new RuntimeException("删除失败", cause);
        }
    }

    private Category wrap(ResultSet rs) throws SQLException {
        Category c = new Category();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        return c;
    }

    @Override
    public List<Category> finaAll() {
        ResultSetHandler<List<Category>> rsHandler = rs -> {
            List<Category> list = new ArrayList<>();
            if( rs.next() ) {
                Category c = wrap(rs);
                list.add(c);
            }
            return list;
        };
        try {
            return runner.query(FIND_ALL,rsHandler);
        } catch (SQLException cause) {
            throw new RuntimeException("删除失败", cause);
        }
    }


}
