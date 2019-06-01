package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
//简单curd用JpaRepository，复杂则加上JpaSpecificationExecutor
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    @Modifying
    @Query(value = "update  tensquare_article.tb_article  set state = 2  where id = ?;",nativeQuery = true)
    void articleCheck(String articleid);

    @Modifying
    @Query(value = "update  tensquare_article.tb_article  set thumbup = thumbup+1 where id = ?;",nativeQuery = true)
    void thumbup(String articleid);

}
