package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiaokuli
 * @date 2019/6/4 - 11:58
 */
@Component
public class ManagerFilter extends ZuulFilter {


    @Autowired
    private JwtUtil jwtUtil;


    /*
    * 定义什么时候过滤
    *   pre ：可以在请求被路由之前调用
        route ：在路由请求时候被调用
        post ：在route和error过滤器之后被调用
        error ：处理请求时发生错误时被调用
    * */
    @Override
    public String filterType() {
        return "pre";
    }


    /*
    * 执行优先级，如有多个的话过滤器的话，0优先执行
    * */
    @Override
    public int filterOrder() {
        return 0;
    }

    /*
    * 是否启用该过滤器
    * */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    /*
    * 过滤器的具体逻辑
    * */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了后台manager的过滤器");
        //获取request域
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        /*？不知是啥  遇到OPTIONS方法直接放行 OPTIONS请求是一种在get/post请求发送时前发送的一种请求
        *   并没有携带相应的authorization认证信息，不需拦截
        * */
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        /*不处理登录请求*/
        if(request.getRequestURI().indexOf("login")>0){ //返回指定字符在字符串出现的位置
            return null;
        }

        //得到头信息
        String authorization = request.getHeader("Authorization");
        if(authorization!=null && !"".equals(authorization)){
            if(authorization.startsWith("Bearer ")){
                String token = authorization.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String role = (String) claims.get("roles");
                    if(role.equals("admin")){
                        //把请求继续往下发，并且放行
                        currentContext.addZuulRequestHeader("Authorization",authorization);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    //终止运行
                    currentContext.setSendZuulResponse(false); //让zuul不再对其进行路由
                }
            }
        }
        currentContext.setSendZuulResponse(false);//让zuul不再对其进行路由
        currentContext.setResponseStatusCode(403); //权限不足代码
        currentContext.setResponseBody("权限不足");
        currentContext.getResponse().setContentType("text/html;charset=utf-8");

        return null;
    }
}
