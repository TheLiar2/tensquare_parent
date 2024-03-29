package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import com.tensquare.qa.client.BaseClient;
import com.tensquare.qa.dao.ProblemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BaseClient baseClient;

	/*调用base微服务的labelController*/
	@RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
	public Result findOneById(@PathVariable("id")String id){
		Result result = baseClient.findById(id);
		return result;
	}

	//最新回答列表
	@RequestMapping(value = "/newlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result newList(@PathVariable("label")String labelid,@PathVariable int page, @PathVariable int size){
		Page<Problem> pageResult = problemService.newList(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageResult.getTotalElements(),pageResult.getContent()));
	}

	//热门回答列表
	@RequestMapping(value = "/hotlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result hotList(@PathVariable("label")String labelid,@PathVariable int page, @PathVariable int size){
		Page<Problem> pageResult = problemService.hotList(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageResult.getTotalElements(),pageResult.getContent()));
	}

	//等待回答列表
	@RequestMapping(value = "/waitlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result waitList(@PathVariable("label")String labelid,@PathVariable int page, @PathVariable int size){
		Page<Problem> pageResult = problemService.waitList(labelid, page, size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageResult.getTotalElements(),pageResult.getContent()));
	}




	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem){
		//判断当前是否用user角色
		String token = (String) request.getAttribute("claims_user");
		System.out.println(token);
		if(token==null || "".equals(token)){
			throw new RuntimeException("权限不足");
		}
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
