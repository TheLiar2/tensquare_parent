package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.List;

/**
 * @author xiaokuli
 * @date 2019/5/24 - 16:39
 */
@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    //查询所有
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    //查询单个
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    //
    /*** 增加* @param spit*/
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId()+"");
        spit.setShare(0);
        spit.setVisits(0);
        spit.setComment(0);
        spit.setThumbup(0);
        spit.setState("1");
        //如果添加当前节点，有父节点的话，父节点comment要加1
        if(spit.getParentid()!=null&&!"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    //根据parentId查询
    public Page<Spit> findByParentId(String parentId,int page ,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentId,pageable);

    }

    //吐槽点赞
    public void thumbup(String spitId){
        //先获取一个对象
        //1.效率问题
//        Spit spit = spitDao.findById(spitId).get();
//        spit.setThumbup((spit.getThumbup()==null?0:spit.getThumbup())+1);
//        spitDao.save(spit);

        //2.使用原生的mongodb的update方法 db.spit.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
        Query query = new Query();

        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
