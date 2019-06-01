package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author xiaokuli
 * @date 2019/5/26 - 23:17
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {

    //根据key搜索
    public Page<Article> findByTitleOrContentLike(String key, String key1, Pageable pageable);
}
