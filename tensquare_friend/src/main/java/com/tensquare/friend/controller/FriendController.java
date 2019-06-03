package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaokuli
 * @date 2019/6/3 - 15:17
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    /*调用user微服务*/
    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable("friendid")String friendid,@PathVariable("type")String type){
        //从request中获取token，判断是否登录或者具有user角色
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims == null){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        String userid = claims.getId();
        //判断是添加好友还是添加非好友
        if(type!=null){
            if(type.equals("1")){
                //添加好友
                int result = friendService.addFriend(userid,friendid);
                if(result == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }
                if(result == 1){
                    userClient.updateFansFollowCount(userid,friendid,1);
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }else if(type.equals("2")){
                //添加非好友
                int result = friendService.addNoFriend(userid, friendid);
                if(result == 0){
                    return new Result(false,StatusCode.ERROR,"不能重复添加非好友");
                }
                if(result == 1){
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else{
            return new Result(false, StatusCode.ERROR,"参数异常");
        }

    }

    @RequestMapping("/{friendid}")
    public Result deleteFriend(@PathVariable("friendid")String friendid){
        //从request中获取token，判断是否登录或者具有user角色
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims == null){
            return new Result(false,StatusCode.ERROR,"权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid,friendid);
        userClient.updateFansFollowCount(userid,friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
