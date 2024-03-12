package org.azerciard.userservice.domain.config;

public class Const {
    public final static String[] WHITE_LIST =new String[]{"/user/register","/user/login","/user/validate-token", "/health/**","/swagger-ui/**","/v3/api-docs/**"};
}
