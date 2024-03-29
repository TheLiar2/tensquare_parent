package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaokuli
 * @date 2019/5/22 - 17:31
 */
@Service
@Transactional
public class LabelService {

   @Autowired
   private LabelDao labelDao;

   @Autowired
   private IdWorker idWorker;

   public List<Label> findAll(){
       return labelDao.findAll();
   }

   public Label findById(String labelId){
       return labelDao.findById(labelId).get();
   }

   public void save(Label label){
       label.setId(idWorker.nextId()+"");
       labelDao.save(label);
   }

   public void update(Label label){
       labelDao.save(label);
   }

   public void deteleById(String labelId){
       labelDao.deleteById(labelId);
   }


   public List<Label> findSearch(Label label){
       return labelDao.findAll(new Specification<Label>() {
           @Override
           // root根类 query查询关键字 cb条件
           public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               //new一个集合，来存放所有的条件
               List<Predicate> list = new ArrayList<>();

               if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                   Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                   list.add(predicate);
               }

               if(label.getState()!=null && !"".equals(label.getState())){
                   Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                   list.add(predicate);
               }

               //new一个数组作为最终返回的条件
               Predicate[] parr = new Predicate[list.size()];
               //将list的内容转为数组给parr
               parr = list.toArray(parr);

               return cb.and(parr);
           }
       });
   }

    public Page<Label> pageQuery(Label label, int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            // root根类 query查询关键字 cb条件
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个集合，来存放所有的条件
                List<Predicate> list = new ArrayList<>();

                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }

                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }

                //new一个数组作为最终返回的条件
                Predicate[] parr = new Predicate[list.size()];
                //将list的内容转为数组给parr
                parr = list.toArray(parr);

                return cb.and(parr);
            }
        },pageable);
    }
}
