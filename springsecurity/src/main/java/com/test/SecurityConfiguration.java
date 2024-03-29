package com.test;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // set your configuration on the auth object

        auth.inMemoryAuthentication()
                .withUser("blah")
                .password("blah")
                .roles("USER")
                .and()
                .withUser("foo")
                .password("foo")
                .roles("ADMIN");

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/admin").hasAnyRole("ADMIN")
               .antMatchers("/user").hasAnyRole("USER","ADMIN")
               .antMatchers("/").permitAll()
               .and().formLogin();

    }
}
