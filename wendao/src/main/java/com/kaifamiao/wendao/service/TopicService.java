package com.kaifamiao.wendao.service;

import com.kaifamiao.wendao.dao.*;
import com.kaifamiao.wendao.entity.Category;
import com.kaifamiao.wendao.entity.Customer;
import com.kaifamiao.wendao.entity.Explain;
import com.kaifamiao.wendao.entity.Topic;
import com.kaifamiao.wendao.utils.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TopicService {
   private TopicsDao topicsDao;
   private ExplainDao explainDao;
   private CategoryDao categoryDao;
   private TopicLikeDao topicLikeDao;
   private ExplainLikeDao explainLikeDao;
   private AttentionDao attentionDao;
   //获得雪花实例
   private SnowflakeIdGenerator snow = SnowflakeIdGenerator.getInstance();
   public TopicService(){
       topicsDao=new TopicsDao();
       explainDao=new ExplainDao();
       categoryDao = new CategoryDao();
       topicLikeDao=new TopicLikeDao();
       explainLikeDao=new ExplainLikeDao();
       attentionDao=new AttentionDao();
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
            topic.setThumbUpCount(topicLikeDao.thumbUPCount(topic.getId()));
            topic.setThumbDownCount(topicLikeDao.thumbDownCount(topic.getId()));
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
    public Topic find(Long along,Long customer_id){
       Topic topic = topicsDao.find(along);
       int state;
       List<Explain> explains = explainDao.findTop(along);
       for(Explain explain:explains){
           if(customer_id!= null){
               LikeExplain likeExplain  = explainLikeDao.find(customer_id,explain.getId());
               if(likeExplain != null){
                   explain.setState(likeExplain.getState());
               }
           }else{
               explain.setState(null);
           }
       }
       topic.setExplains(explains);
       return topic;

    }
    //分页查询
    public Paging<Topic> findPage(Integer size, Integer current, Customer customer, Integer page){
       Paging<Topic> paging=new Paging<>();
       Integer begin=(current-1)*size;
        Integer count;
        Long customer_id;
       if (customer==null){
           count=findCount(null);
           customer_id = null;
       }else {
           count=findCount(customer.getId());
           customer_id=customer.getId();
       }
       Integer total=count/size;
       total= count%size==0? total:total+1;
       paging.setSize(size);
       paging.setCurrent(current);
       paging.setTotal(total);
       paging.setBegin(begin);
       List<Topic> list=topicsDao.findPage(begin,size,customer_id,page);
        for (Topic t:list) {
            Customer customer1=t.getAuthor();
            customer1.setConcern(0);
            List<Explain> explains = explainDao.findTop(t.getId());
            t.setExplains(explains);
            Integer cl;
            if(explains != null){
                cl=explains.size();
            }else{
                cl=0;
            }
            t.setExplainCount(cl);
            t.setCategory_name(categoryDao.find(t.getCategory_id()).getName());
            //查询话题的点赞数量
            t.setThumbUpCount(topicLikeDao.thumbUPCount(t.getId()));
            //查询话题的踩的数量
            t.setThumbDownCount(topicLikeDao.thumbDownCount(t.getId()));
            //获取话题的赞或踩的信息的状态的列表
            if(customer_id != null){
                Long id=t.getAuthor().getId();
                Attention  attention=attentionDao.findSecond(customer_id,id);
                int  attenState= attention==null? 0:1;
                customer1.setConcern( attenState);
                t.setAuthor(customer1);
                Praise praise = topicLikeDao.find(customer_id,t.getId());
                if(praise != null){
                    t.setState(praise.getState());
                    continue;
                }
            }
            t.setState(null);
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
        for (Topic t:list) {
            t.setCategory_name(categoryDao.find(t.getCategory_id()).getName());
        }
        paging.setDataList(list);
        return paging;
   }
    private Integer findCountLike(String keyWord){
       return topicsDao.findCountLike(keyWord);
    }

    public List<Category> loadCategoryList() {
        return categoryDao.finaAll();
    }

    public  Category loadCategory(Long id){
       return categoryDao.find(id);
    }

    public List<Topic> hotTopic(){
        List<Topic> source =  topicsDao.findPage(0,100,null,1);

        for (Topic t:source) {
            t.setCategory_name(categoryDao.find(t.getCategory_id()).getName());
            t.setThumbUpCount(topicLikeDao.thumbUPCount(t.getId()));
            t.setThumbDownCount(topicLikeDao.thumbDownCount(t.getId()));
            if(t.getAuthor().getId() != null){
                Praise praise = topicLikeDao.find(t.getAuthor().getId(),t.getId());
                if(praise != null){
                    t.setState(praise.getState());
                    continue;
                }
            }
            t.setState(null);
        }
        Topic[] sourceArray= new Topic[source.size()];
        sourceArray = source.toArray(sourceArray);
        quicklysort(sourceArray,0,sourceArray.length-1);
        return List.of(sourceArray).subList(0,source.size()>10?10:source.size());
    }

    private int hotTop(Topic t){
         return t.getPriority()+t.getThumbUpCount()*9+t.getThumbDownCount()*11;
    }

    public void quicklysort(Topic[] arr,int l,int h )
    {
        for(int i = 1;i<arr.length;i++)
        {
            for(int j =i;j>0;j--)
            {
                if(hotTop(arr[j])>hotTop(arr[j-1]))
                {
                    Topic x = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = x;
                }
            }
        }
    }

    public boolean delete(Long customer_id,Long topic_id){
        //删除话题的相关评论
        List<Explain> explainList = explainDao.findTop(topic_id);
        //删除话题的评论及相关的点赞信息
        for (Explain explain : explainList) {
            LikeExplain explainLike = new LikeExplain();
            explainLike.setExplain_id(explain.getId());
            //删除每个评论的点赞信息
            explainLikeDao.delete(explain.getId(),2);
            //删除评论
            explainDao.delete(explain.getId());
        }
        //获取每个话题的点赞信息列
        topicLikeDao.delete(topic_id, 2);
        //删除话题
        return topicsDao.delete(topic_id);
    }
    public Paging<Topic> search(Integer size, Integer current,Long cate,Long customer_id){
        Paging<Topic> paging=new Paging<>();
        Integer begin=(current-1)*size;
        Integer count=searchCount(cate);
        Integer total=count/size;
        total= count%size==0? total:total+1;
        paging.setSize(size);
        paging.setCurrent(current);
        paging.setTotal(total);
        paging.setBegin(begin);
        List<Topic> list=topicsDao.searchPage(cate,begin,size);
        for (Topic t:list) {
            Customer customer1=t.getAuthor();
            customer1.setConcern(0);
            t.setCategory_name(categoryDao.find(t.getCategory_id()).getName());
            //查询话题的点赞数量
            t.setThumbUpCount(topicLikeDao.thumbUPCount(t.getId()));
            //查询话题的踩的数量
            t.setThumbDownCount(topicLikeDao.thumbDownCount(t.getId()));
            //获取话题的赞或踩的信息的状态的列表
            if(customer_id != null){
                Long id=t.getAuthor().getId();
                Attention  attention=attentionDao.findSecond(customer_id,id);
                int  attenState= attention==null? 0:1;
                customer1.setConcern( attenState);
                t.setAuthor(customer1);
                Praise praise = topicLikeDao.find(customer_id,t.getId());
                if(praise != null){
                    t.setState(praise.getState());
                    continue;
                }
            }
            t.setState(null);
        }
        paging.setDataList(list);
        return paging;
    }
    private Integer searchCount(Long cate){
      return topicsDao.searchCount(cate);
    }

    public Topic searchOne(Long topic_id ,Long customer_id){
       Topic topic = topicsDao.find(topic_id);
        Customer customer1=topic.getAuthor();
        customer1.setConcern(0);
        topic.setCategory_name(categoryDao.find(topic.getCategory_id()).getName());
        //查询话题的点赞数量
        topic.setThumbUpCount(topicLikeDao.thumbUPCount(topic.getId()));
        //查询话题的踩的数量
        topic.setThumbDownCount(topicLikeDao.thumbDownCount(topic.getId()));
        //获取话题的赞或踩的信息的状态的列表
        if(customer_id != null){
            Long id=topic.getAuthor().getId();
            Attention  attention=attentionDao.findSecond(customer_id,id);
            int  attenState= attention==null? 0:1;
            customer1.setConcern( attenState);
            topic.setAuthor(customer1);
            Praise praise = topicLikeDao.find(customer_id,topic.getId());
            if(praise != null){
                topic.setState(praise.getState());
            }
        }
        topic.setState(null);
        return topic;
    }

}
