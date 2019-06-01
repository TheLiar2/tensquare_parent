package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    //推荐职位
    public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state); //where state = xx 通过state状态查询

    //最新职位 按时间降序排序
    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);// where state!= xx
	
}
