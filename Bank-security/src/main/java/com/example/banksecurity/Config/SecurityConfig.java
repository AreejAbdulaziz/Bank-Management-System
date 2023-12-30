package com.example.banksecurity.Config;

import com.example.banksecurity.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/employee/register","/api/v1/customer/register").permitAll()
                .requestMatchers("/api/v1/employee/get","/api/v1/customer/get","/api/v1/account/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/account/getMyAccounts","/api/v1/account/add","/api/v1/account/update/{id}","/api/v1/account/deposit/{id}/{amount}","/api/v1/account/withdraw/{id}/{amount}","/api/v1/account/transfer/{account_id1}/{account_id2}/{amount}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/account/delete/{id}","/api/v1/account/active/{id}","/api/v1/customer/update/{id}","/api/v1/customer/delete/{id}").hasAnyAuthority("CUSTOMER","ADMIN")
                .requestMatchers("/api/v1/employee/update/{id}","/api/v1/employee/delete/{id}").hasAnyAuthority("EMPLOYEE","ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }

}
