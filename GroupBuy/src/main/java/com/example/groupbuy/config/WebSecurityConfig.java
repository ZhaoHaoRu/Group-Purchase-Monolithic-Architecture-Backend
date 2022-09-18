package com.example.groupbuy.config;

import com.example.groupbuy.filter.JwtLoginFilter;
import com.example.groupbuy.filter.JwtVerifyFilter;
import com.example.groupbuy.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Robod
 * @date 2020/8/9 15:47
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Resource
    private final UserAuthService userService;

    private final RsaKeyProperties rsaKeyProperties;

    public WebSecurityConfig(UserAuthService userService, RsaKeyProperties rsaKeyProperties) {
        this.userService = userService;
        this.rsaKeyProperties = rsaKeyProperties;
    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 认证用户的来源
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //数据库中
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }
//
//    /**
//     * 配置SpringSecurity相关信息
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()  //关闭csrf
//                .addFilter(new JwtLoginFilter(super.authenticationManager(),rsaKeyProperties))
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //禁用session
//    }
    @Configuration
    @Order(1)
    public static class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {

        public final UserAuthService userService;

        public final RsaKeyProperties rsaKeyProperties;

        public  DefaultWebSecurityConfig(UserAuthService userService, RsaKeyProperties rsaKeyProperties) {
            this.userService = userService;
            this.rsaKeyProperties = rsaKeyProperties;
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            //数据库中
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/login");
            http.csrf().disable()  //关闭csrf
                    .authorizeRequests()
                    .antMatchers("/login").hasAnyRole("USER")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilter(new JwtLoginFilter(super.authenticationManager(),rsaKeyProperties))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//禁用session

        }
    }

    @Configuration
    @Order(2)
    public static class DefaultWebSecurityConfig2 extends WebSecurityConfigurerAdapter {

        public final RsaKeyProperties rsaKeyProperties;

        public  DefaultWebSecurityConfig2(RsaKeyProperties rsaKeyProperties) {
            this.rsaKeyProperties = rsaKeyProperties;
        }


        /**
         * 配置SpringSecurity相关信息
         *
         * @param http
         * @throws Exception
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()  //关闭csrf
                    .authorizeRequests()
                    .antMatchers("/group/**", "/order/**", "/seckill/**")
                    .authenticated()    //表示其它资源认证通过后
                    .and()
                    .addFilter(new JwtVerifyFilter(super.authenticationManager(),rsaKeyProperties));
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //禁用session
        }
    }


}
