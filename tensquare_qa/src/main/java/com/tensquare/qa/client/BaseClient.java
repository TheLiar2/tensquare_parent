package com.tensquare.qa.client;

import com.tensquare.qa.clientimpl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xiaokuli
 * @date 2019/6/2 - 12:17
 */
@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    @RequestMapping(value="/label/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String id);
}
