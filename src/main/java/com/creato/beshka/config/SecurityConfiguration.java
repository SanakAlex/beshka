package com.creato.beshka.config;

import com.creato.beshka.persistence.dao.UserRepository;
import com.creato.beshka.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final UserRepository userRepository;
    final private CustomUserDetailsService userDetailsService;
    private final CORSFilter corsFilter;

    @Autowired
    public SecurityConfiguration(UserRepository userRepository, CustomUserDetailsService userDetailsService, CORSFilter corsFilter) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.corsFilter = corsFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/static/**", "/favicon.ico").permitAll()
                    .anyRequest().authenticated()//.permitAll()
                .and()
                    .cors()
                .and()
                    .httpBasic()
                .and()
                    .formLogin()
//                        .loginPage("/")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout").permitAll()
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                .and()
                    .rememberMe();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
