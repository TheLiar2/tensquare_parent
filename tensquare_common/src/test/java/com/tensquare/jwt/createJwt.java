package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author xiaokuli
 * @date 2019/5/29 - 23:23
 */
public class createJwt {

    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder().setId("123456").setSubject("小甘").setIssuedAt(new Date()).claim("role","admin").setExpiration(new Date(new Date().getTime()+60000)).signWith(SignatureAlgorithm.HS256, "theliar");

        System.out.println(new Date());
        System.out.println(new Date().getTime());

        System.out.println(jwtBuilder.compact());
    }
}
