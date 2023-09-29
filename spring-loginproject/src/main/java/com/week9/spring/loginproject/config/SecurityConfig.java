
package com.week9.spring.loginproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
     SuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http, UserDetailsService userDetailsService) throws Exception{
        http.authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                        .requestMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                                        .requestMatchers("/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .formLogin(
                        formlog ->
                                formlog
                                        .loginPage("/signup")
                                        .loginProcessingUrl("/login")
                                        .successHandler(successHandler)
                                        .permitAll()
                )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/login?logout")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")

                )
                .rememberMe(
                        rememberme ->
                                rememberme
                                        .rememberMeServices(rememberMeServices(userDetailsService))
                                        .userDetailsService(userDetailsService)
                                        .key("token")
                );
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
     RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("token",
                userDetailsService);
        rememberMeServices.setAlwaysRemember(true);

        return rememberMeServices;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebSecurityCustomizer ignoringCustiomizer(){
        return (web -> web.ignoring().requestMatchers("/css/**","/js/**"));
    }
    @Autowired
    public UserDetailsService userDetails;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProv = new DaoAuthenticationProvider();

        authenticationProv.setUserDetailsService(userDetails);

        authenticationProv.setPasswordEncoder(passwordEncoder());

        return authenticationProv;
    }

}
