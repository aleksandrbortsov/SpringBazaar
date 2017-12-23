package com.springbazaar.configuration;

import com.springbazaar.web.ui.LoginUI;
import com.springbazaar.web.ui.RegistrationUI;
import com.springbazaar.web.ui.WelcomeUI;
import com.springbazaar.web.ui.editor.ProductEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Autowired
//    public DaoAuthenticationProvider createDaoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(getPasswordEncoder());
//        return provider;
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // disable CSRF (Cross-Site Request Forgery) since Vaadin implements its own mechanism for this
                .csrf().disable()
                // let Vaadin be responsible for creating and managing its own sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()

                .exceptionHandling().accessDeniedPage("/accessDenied")
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LoginUI.NAME))
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl(LoginUI.NAME)
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/VAADIN/**",
                        "/PUSH/**",
                        "/UIDL/**",
                        "/login",
                        "/login/**",
                        "/error/**",
                        "/accessDenied/**",
                        "/vaadinServlet/**").permitAll()
//                TODO after setup login delete /welcome etc views
                .antMatchers(RegistrationUI.NAME).permitAll()
                .anyRequest().authenticated();
    }
}
