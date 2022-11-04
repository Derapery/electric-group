package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.BadLogDao;
import com.kaifamiao.wendao.dao.ExplainDao;
import com.kaifamiao.wendao.dao.ExplainLikeDao;
import com.kaifamiao.wendao.entity.BadLog;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.utils.Constants;
import com.kaifamiao.wendao.utils.LikeExplain;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class ExplainService {
    private SnowflakeIdGenerator snowflakeIdGenerator;
    private ExplainDao explainDao;
    private ExplainLikeDao explainLikeDao;
    private BadLogDao badLogDao;
    private static final String[] ZANGHUA = {"\u6211\u64cd","\u5367\u69fd","\u82cf\u5f66\u4f1f","\u50bb\u903c","\u72d7\u4e1c\u897f"};

    public ExplainService(){
        super();
        snowflakeIdGenerator=SnowflakeIdGenerator.getInstance();
        explainDao=new ExplainDao();
        explainDao=new ExplainDao();
        explainLikeDao=new ExplainLikeDao();
        badLogDao = new BadLogDao();
    }
    //删除评论
    public boolean delete(Long explain_id){
            //删除话题相关的点赞信息
            explainLikeDao.delete(explain_id,2);
            //删除话题
            return explainDao.delete(explain_id);
    }
//保存评论
    public boolean save(Explain explain){
        LocalDateTime localDateTime=LocalDateTime.now();
        explain.setPublishTime(localDateTime);
        explain.setId(snowflakeIdGenerator.generate());
        String newContent = rebulidContent(explain.getContent());
        if(!checkContent(explain.getContent(),newContent)){
            explain.setContent(rebulidContent(explain.getContent()));
            setBadLog(explain);
        }
        return explainDao.save(explain);
    }

    private void setBadLog(Explain explain){
        BadLog badLog = new BadLog();
        badLog.setType(Constants.BADLOG_TYPE.getValue());
        badLog.setId(snowflakeIdGenerator.generate());
        badLog.setUser_id(explain.getAuthor().getId());
        badLogDao.save(badLog);
    }
    //查找评论
    public Explain find(Long id){
        return explainDao.find(id);
    }
    //修改评论的点赞和踩的数量
    public boolean modify(Integer count,Long id,int tage){
        return explainDao.modify(count,id,tage);
    }

    public static Boolean checkContent(String content,String newContent) {
        return content.equals(newContent);
    }
    public static String rebulidContent(String content) {
        for (String s:ZANGHUA) {
            content = content.replaceAll(s, "\\*".repeat(s.length()));
        }
        return content;
    }
}

