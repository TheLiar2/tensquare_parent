package com.tensquare.qa.Interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaokuli
 * @date 2019/5/30 - 13:01
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    public JwtUtil jwtUtil;

    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过Jwt拦截器");
        //无论如何都要放行，具体是否能操作在具体操作中判断
        //拦截器只是负责把头信息中的token令牌进行解析
        //先获取头信息
        String authorization = request.getHeader("Authorization");
        if(authorization!=null && !"".equals(authorization)){
            if(authorization.startsWith("Bearer ")){
                String token = authorization.substring(7);
                System.out.println(token);
                //对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles!=null && roles.equals("admin")){
                        request.setAttribute("claims_admin",token);
                    }
                    if(roles!=null && roles.equals("user")){
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌有误");
                }
            }
        }
        return true;
    }
}
