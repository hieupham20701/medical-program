package com.medical.program.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.medical.program.model.entity.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class JWTTokenCreator {
    public enum TokenType {
        ACCESS_TOKEN, REFRESH_TOKEN
    }
    private Employee employee = null;

    public JWTTokenCreator(Employee employee) {
        this.employee = employee;
    }

    public String createToken(TokenType tokenType){
        Collection<String> rolesOfUser = new ArrayList<>();
        rolesOfUser.add(employee.getRole().name());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String returnToken = null;

        switch (tokenType){
            case ACCESS_TOKEN: {
                returnToken = JWT.create()
                        .withClaim("userId", employee.getId())
                        .withClaim("name", employee.getFullName())
                        .withClaim("phone", employee.getPhoneNumber())
                        .withIssuedAt(new Date(System.currentTimeMillis()))
                        .withExpiresAt(new Date(System.currentTimeMillis() +100*60*1000)) //changed it to make lifetime of access token is longer
                        .withClaim("roles", new ArrayList<>(rolesOfUser))
                        .sign(algorithm);
                break;
            }

            case REFRESH_TOKEN: {
                returnToken = JWT.create()
                        .withClaim("userId", employee.getId())
                        .withClaim("name", employee.getFullName())
                        .withClaim("phone", employee.getPhoneNumber())
//                        .withClaim("tokenVersion", employee.getTokenVersion())
                        .withClaim("roles", new ArrayList<>(rolesOfUser))
                        .withIssuedAt(new Date(System.currentTimeMillis()))
                        .withExpiresAt(new Date(System.currentTimeMillis() +3000*60*1000))
                        .sign(algorithm);
                break;
            }
        }

        return returnToken;
    }

}
