package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	/*
	* 更新好友粉丝数和用户关注数*/
    //因为不用像前端返回东西，故void就可以
	@RequestMapping("/updateFansFollowCount/{userid}/{friendid}/{x}")
    public void updateFansFollowCount(@PathVariable("userid")String userid,@PathVariable("friendid")String friendid,@PathVariable("x")int x){
		userService.updateFansFollowCount(x,userid,friendid);
	}

	/*用户登录*/
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result sendSms(@RequestBody User user){
		User userLogin = userService.login(user.getMobile(),user.getPassword());
		if(userLogin!=null){
			//创建jwt
			String token = jwtUtil.createJWT(userLogin.getId(), userLogin.getNickname(), "user");
			Map<String,String> map = new HashMap<>();
			map.put("token",token);
			map.put("roles","user");
			return new Result(true,StatusCode.OK,"登录成功",map);
		}

		return new Result(false,StatusCode.LOGINERROR,"登录失败");

	}

    /*发送验证码*/
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

    /*用户注册*/
	@RequestMapping("/register/{code}")
	public Result register(@PathVariable String code,@RequestBody User user){
		String checkCode = (String) redisTemplate.opsForValue().get("checked_"+user.getMobile());
		if(checkCode.isEmpty()){
			return new Result(false,StatusCode.ERROR,"请发送验证码");
		}
		if(!checkCode.equals(code)){
			return new Result(false,StatusCode.ERROR,"验证码不对");
		}
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除  判断必须有admin角色才能删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
