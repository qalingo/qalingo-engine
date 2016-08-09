package org.hoteia.qalingo.core.security.fo.configuration;

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
                "/home.html**",
                "/index.html**",
                "/contact.html**",
                "/faq.html**",
                "/follow-us.html**",
                "/legal-terms.html**",
                "/logout.html**",
                "/forbidden.html**",
                "/our-company.html**",
                "/welcome.html**",
                "/change-language.html**",
                "/change-context.html**").permitAll() 
        
        .antMatchers("/documents/*",
                "/**/retailer-create.html*",
                "/**/retailer-comment*",
                "/**/retailer-vote*",
                "/**/retailer-contact*",
                "/**/product-comment*",
                "/**/product-vote*",
                "/**/cart-delivery-order-information*",
                "/**/cart-order-confirmation*",
                "/**/cart-order-payment*",
                "/**/personal*",
                "/**/remove-from-wishlist*",
                "/**/add-to-wishlist*").access("hasRole('ROLE_FO_CUSTOMER')")

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