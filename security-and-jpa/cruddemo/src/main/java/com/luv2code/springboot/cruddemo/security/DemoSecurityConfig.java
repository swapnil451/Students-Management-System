package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

//    While accessing the endpoints you will be asked endpoints and password and out of the above three person if anyone
//    gives the correct password then all the employees list will be visible to them
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource)
    {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

//  DEFINE QUERY TO RETRIEVE USER BY USERNAME like john or mary
//  IN THE NEXT QUERY IN SQL QUERY WE NEED TO GIVE THE FIELD THAT CONTAINS USERNAME , SECOND IS PASSWORD AND THIRD IS
//  INTEGER WHICH IS STATES WHETHER USER ACTIVE OR NOT 
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id,pw,active from members where user_id=?");

//  DEFINE QUERY TO RETRIEVE AUTHORITIES/ROLES BY USERNAME LIKE john or mary
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception
    {
        http.authorizeHttpRequests( configurer ->
                configurer
//                        GET ALL EMPLOYEES
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
// ** in the next line we can get anything after api/employees and it will be acceptable like api/employees/1/2/2
// o r anything like api/employees/1234/bla
//                        GET EMPLOYEE BY ID
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")//INSERT
                        .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")//UPDATE
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")//DELETE
                        .requestMatchers(HttpMethod.PATCH, "/api/employees/**").hasRole("MANAGER")
        );

//  use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

//   disable Cross site request Forgerey CSRF
//   in general not required for stateless REST API
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
