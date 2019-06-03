package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiaokuli
 * @date 2019/6/3 - 17:41
 */
@FeignClient("tensquare-user")
public interface UserClient {

    @RequestMapping("/user/updateFansFollowCount/{userid}/{friendid}/{x}")
    public void updateFansFollowCount(@PathVariable("userid")String userid, @PathVariable("friendid")String friendid, @PathVariable("x")int x);
}
