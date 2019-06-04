package com.tensquare.qa.clientimpl;

import com.tensquare.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @author xiaokuli
 * @date 2019/6/4 - 10:53
 */
@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"触发了熔断器");
    }
}
