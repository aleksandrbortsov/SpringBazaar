package com.springbazaar.configuration;

import com.springbazaar.web.ui.LoginUI;
import com.springbazaar.web.ui.RegistrationUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableGlobalAuthentication
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String REMEMBER_ME_APP_KEY = "rememberMeAppKey";
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    @Qualifier("authenticationProvider")
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfiguration(DataSource dataSource,
                                    UserDetailsService userDetailsService,
                                    AuthenticationProvider authenticationProvider) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authenticationProvider);
    }

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
                .rememberMe().rememberMeServices(rememberMeServices()).key(REMEMBER_ME_APP_KEY)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl(LoginUI.NAME).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/VAADIN/**",
                        "/PUSH/**",
                        "/UIDL/**",
                        "/login",
                        "/login/**",
                        "/error/**",
                        "/accessDenied/**",
                        "/vaadinServlet/**",
                        RegistrationUI.NAME).permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new PersistentTokenBasedRememberMeServices(REMEMBER_ME_APP_KEY, userDetailsService,
                persistentTokenRepository());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
