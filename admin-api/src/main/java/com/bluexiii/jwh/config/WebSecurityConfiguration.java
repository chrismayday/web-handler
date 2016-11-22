package com.bluexiii.jwh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity//(debug = true) //@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile("security")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.requestMatchers().anyRequest();

        //http.csrf().ignoringAntMatchers("/oauth/**");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.httpBasic();
        http.formLogin().loginPage("/login").defaultSuccessUrl("/",true).failureUrl("/login?error").permitAll();  //提供登录界面

        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .hasRole("ADMIN")
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/**")
                .authenticated()
            .and()
                .authorizeRequests()
                .antMatchers("/druid/**")
                .hasRole("ADMIN")
            .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**")
                .hasRole("ADMIN")
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        // @formatter:off
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //允许Swagger,测试用
//        web.ignoring().antMatchers("/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**");
        //允许直接调用API,测试用
//        web.ignoring().antMatchers("/v1/**");
        //允许druid监控,测试用
//        web.ignoring().antMatchers("/druid/**");
        //允许H2控制台,测试用
//        web.ignoring().antMatchers("/h2-console/**");
    }


    /**
     * 解决HTTPS下登录后跳转至HTTP的问题
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
        handler.setUseReferer(true);
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }

}