package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author xiaokuli
 * @date 2019/5/24 - 16:36
 */
public interface SpitDao extends MongoRepository<Spit,String>{

    //根据parenid查
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
