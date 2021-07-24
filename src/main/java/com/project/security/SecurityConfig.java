package com.project.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String WAREHOUSE_ROLE = "WAREHOUSE";
    private final String SALES_ROLE = "SALES";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("inventory/**").hasRole(WAREHOUSE_ROLE)
                .antMatchers("sales/**").hasRole(SALES_ROLE)
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("sales")
                .password("{noop}prodaja")
                .roles(SALES_ROLE)
                .and()
                .withUser("warehouse")
                .password("{noop}skladiste")
                .roles(WAREHOUSE_ROLE);
    }

}
