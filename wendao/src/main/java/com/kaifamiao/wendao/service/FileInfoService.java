package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.FileInfoDao;
import com.kaifamiao.wendao.entity.FileInfo;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;
import org.tinylog.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class FileInfoService {

    private FileInfoDao fiDao = new FileInfoDao();
    private SnowflakeIdGenerator idGenerator = SnowflakeIdGenerator.getInstance();


    public String naming( String original, String checksum ){
        int index = original.lastIndexOf( '.' );
        String suffix = "";
        if( index != -1 ) {
            suffix = original.substring( index );
        }
        return checksum + suffix;
    }

    public void store( String pathname, byte[] bytes ) {
        Logger.trace( "保存文件内容到服务器指定位置" );
        try {
            OutputStream out = new FileOutputStream(pathname);
            out.write(bytes, 0, bytes.length);
            out.close();
        } catch ( IOException cause ) {
            throw new RuntimeException( "文件内容保存失败", cause );
        }
    }


    public void store(FileInfo fi){
        Logger.trace( "保存文件信息到数据库中" );
        fi.setId(idGenerator.generate());
        fi.setUploadTime(LocalDateTime.now());
        fiDao.save(fi);
    }

    public FileInfo load(Long id) {
        return fiDao.find(id);
    }

}
