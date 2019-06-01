package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @author xiaokuli
 * @date 2019/5/29 - 23:34
 * Thu May 30 00:07:51 CST 2019
 * 1559146071993
 */
public class ParseJwt {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("theliar").parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJzdWIiOiLlsI_nlJgiLCJpYXQiOjE1NTkxNDY4NTgsInJvbGUiOiJhZG1pbiIsImV4cCI6MTU1OTE0NjkxOH0.gaRvBD1xMgAPqd19fZMCeDmG3FUmWbLayxDf2J79UAQ").getBody();

        System.out.println("用户id:"+claims.getId());
        System.out.println("用户名:"+claims.getSubject());
        System.out.println("登录时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色:"+claims.get("role"));
    }
}
