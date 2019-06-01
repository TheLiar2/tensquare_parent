package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaokuli
 * @date 2019/5/26 - 23:21
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //开发中不建议一个个添数据到索引库，因为可以用logstash同步数据库
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
      articleService.save(article);
      return new Result(true,200,"添加成功");
    }

    //开发中主要用到的是 传入key 在站内搜索
    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result search(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData = articleService.search(key,page,size);
        return new Result(true,200,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}
