package com.kaifamiao.wendao.dao;

import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.FileInfo;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class FileInfoDao extends BaseDao<FileInfo,Long>{

    private static final String FETCH_BY_ID = "SELECT id, path, name, checksum, file_size, upload_time, customer_id FROM t_file_infos WHERE id = ?";
    private static final String INSERT = "INSERT INTO t_file_infos (id, path, name, checksum, file_size, upload_time, customer_id) VALUES (?,?,?,?,?,?,?)";

    private ResultSetHandler<FileInfo> singleHandler = rs -> {
        if(rs.next()) {
            FileInfo fi = new FileInfo();
            fi.setId( rs.getLong("id") );
            fi.setPath( rs.getString("path") );
            fi.setName( rs.getString("name") );
            fi.setChecksum( rs.getString("checksum") );
            fi.setSize( rs.getLong("file_size") );
            java.sql.Timestamp ts = rs.getTimestamp("upload_time");
            LocalDateTime updateTime = ts == null ? null : ts.toLocalDateTime();
            fi.setUploadTime(updateTime);
            long cid = rs.getLong("customer_id");
            Customer c = new Customer();
            c.setId(cid);
            fi.setOwner(c);
            return fi;
        }
        return null;
    };

    @Override
    public boolean save(FileInfo f) {
        LocalDateTime uploadTime = f.getUploadTime();
        java.sql.Timestamp time = uploadTime == null ? null : java.sql.Timestamp.valueOf(uploadTime);
        Object[] params = {f.getId(), f.getPath(), f.getName(), f.getChecksum(), f.getSize(),time, f.getOwner().getId()};
        try {
            return runner.update(INSERT, params) == 1 ;
        } catch ( SQLException cause ) {
            throw new RuntimeException("保存失败", cause);
        }
    }

    @Override
    public boolean modify(FileInfo fileInfo) {
        return false;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }


    @Override
    public FileInfo find(Long id) {
        try {
            return runner.query(FETCH_BY_ID, singleHandler, id );
        } catch (SQLException cause) {
            throw new RuntimeException("查询失败", cause);
        }
    }

    @Override
    public List<FileInfo> finaAll() {
        return null;
    }
}
