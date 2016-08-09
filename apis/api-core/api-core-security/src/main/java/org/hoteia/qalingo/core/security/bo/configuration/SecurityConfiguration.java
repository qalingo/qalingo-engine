package org.hoteia.qalingo.core.security.bo.configuration;

import org.hoteia.qalingo.core.security.fo.component.AccessDeniedHandler;
import org.hoteia.qalingo.core.security.fo.component.LoginUrlAuthenticationEntryPoint;
import org.hoteia.qalingo.core.security.fo.component.LogoutSuccessHandler;
import org.hoteia.qalingo.core.security.fo.component.SimpleUrlAuthenticationFailureHandler;
import org.hoteia.qalingo.core.security.fo.component.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AccessDeniedHandler accessDeniedHandler;
    
    @Autowired
    protected LogoutSuccessHandler logoutSuccessHandler;
    
    @Autowired
    protected SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler;
    
    @Autowired
    protected SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler;
    
    @Autowired
    protected LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
      http.exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint)
      .and()
      .authorizeRequests()
        .antMatchers("/", 
                "/login.html**").permitAll()

        .antMatchers("/*").access("hasRole('ROLE_BO_TECHNICAL_ADMIN') or hasRole('ROLE_BO_TECHNICAL_USER')" +
        		"or hasRole('ROLE_BO_BUSINESS_ADMIN') or hasRole('ROLE_BO_BUSINESS_USER')")

        .and()
        .formLogin().loginProcessingUrl("/j_spring_security_check")
        .failureHandler(simpleUrlAuthenticationFailureHandler).successHandler(simpleUrlAuthenticationSuccessHandler)
        .and()
        .logout().logoutSuccessHandler(logoutSuccessHandler).invalidateHttpSession(false).logoutUrl("/logout-session.html")
        .and()
        .rememberMe()
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
        .and()
        .csrf().disable()
        .sessionManagement().sessionFixation().none()
        ;
    }
    
}