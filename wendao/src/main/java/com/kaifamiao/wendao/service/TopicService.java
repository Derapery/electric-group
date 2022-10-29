package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.CategoryDao;
import com.kaifamiao.wendao.dao.ExplainDao;
import com.kaifamiao.wendao.dao.TopicsDao;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.Paging;
import com.kaifamiao.wendao.utils.SnowflakeIdGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class TopicService {
   private TopicsDao topicsDao;
   private ExplainDao explainDao;
   private CategoryDao categoryDao;
   //获得雪花实例
   private SnowflakeIdGenerator snow = SnowflakeIdGenerator.getInstance();
   public TopicService(){
       topicsDao=new TopicsDao();
       explainDao=new ExplainDao();
       categoryDao = new CategoryDao();
   }
   //保存话题
   public boolean save(Topic topic){
      //给话题设置ID
       topic.setId(snow.generate());
       //设置话题的发布的时间
       topic.setPublishTime(LocalDateTime.now());
       //调用DAO层的方法进行保存
      return topicsDao.save(topic);
   }
   //删除话题
    public boolean delete(Long along){
       //删除话题首先删除话题相关的评论
        explainDao.deleteTop(along);
        //删除话题
      return topicsDao.delete(along);
    }
    //查看所有的话题
    public List<Topic> finaAll(){
      List<Topic> list=topicsDao.finaAll();
        for (Topic topic:list) {
            topic.setExplains(explainDao.findTop(topic.getId()));
        }
        return list;
   }
    //查看当前用户发表的话题
    public List<Topic> findUser(Long along ){
      List<Topic> list=topicsDao.findUser(along);
        for (Topic topic:list) {
            topic.setExplains(explainDao.findTop(topic.getId()));
        }
        return list;
    }
    //查看话题详情
    public Topic find(Long along){
       Topic topic = topicsDao.find(along);
       topic.setExplains(explainDao.findTop(along));
       return topic;

    }
    //分页查询
    public Paging<Topic> findPage(Integer size,Integer current,Long customer_id){
       Paging<Topic> paging=new Paging<>();
       Integer begin=(current-1)*size;
       Integer count=findCount(customer_id);
       Integer total=count/size;
       total= count%size==0? total:total+1;
       paging.setSize(size);
       paging.setCurrent(current);
       paging.setTotal(total);
       paging.setBegin(begin);
       List<Topic> list=topicsDao.findPage(begin,size,customer_id);
        for (Topic t:list) {
            t.setCategory_name(categoryDao.find(t.getCategory_id()).getName());
        }
       paging.setDataList(list);
       return paging;
    }
    //查询表中的数据的数目
    public Integer findCount(Long along){
        return topicsDao.findCount(along);
    }
    //用来对用户想要搜索的话题进行模糊查询
    public Paging<Topic> findPageLike(String keyWord,Integer size,Integer current){
        Paging<Topic> paging=new Paging<>();
        Integer begin=(current-1)*size;
        Integer count=findCountLike(keyWord);
        Integer total=count/size;
        total= count%size==0? total:total+1;
        paging.setSize(size);
        paging.setCurrent(current);
        paging.setTotal(total);
        paging.setBegin(begin);
        List<Topic> list=topicsDao.findPageLike(begin,size,keyWord);
        paging.setDataList(list);
        return paging;
   }
    private Integer findCountLike(String keyWord){
       return topicsDao.findCountLike(keyWord);
    }
}
