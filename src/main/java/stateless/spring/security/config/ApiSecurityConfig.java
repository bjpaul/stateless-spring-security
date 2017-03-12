package stateless.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import stateless.spring.security.enums.Authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

import stateless.spring.security.exception.CustomAccessDeniedHandler;
import stateless.spring.security.exception.CustomAuthenticationFailureHandler;
import stateless.spring.security.filter.StatelessAuthenticationFilter;
import stateless.spring.security.filter.StatelessLoginFilter;
import stateless.spring.security.repository.CredentialsRepository;
import stateless.spring.security.repository.TokenRepository;
import stateless.spring.security.service.CustomAuthenticationProvider;
import stateless.spring.security.service.crypto.BCryptPasswordResolver;
import stateless.spring.security.service.crypto.PasswordResolver;
import stateless.spring.security.service.crypto.TextEncryptPasswordResolver;
import stateless.spring.security.service.token.PersistentTokenService;
import stateless.spring.security.service.token.TokenAuthenticationService;

/**
 * This application is secured at both the URL level for some parts, and the
 * method level for other parts. The URL security is shown inside this code,
 * while method-level annotations are enabled at by
 * {@link EnableGlobalMethodSecurity}.
 *
 * @author Bijoy Paul
 */
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${token.validity}")
    private long tokenValidity;

    @Value("${token.secret}")
    private String tokenSecret;

    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider(credentialsRepository, passwordResolver()));
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService(){
        return new PersistentTokenService(credentialsRepository, tokenRepository, tokenValidity);
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public PasswordResolver passwordResolver(){
        return new TextEncryptPasswordResolver(tokenSecret);
//        return new BCryptPasswordResolver(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/login", "/employee/count").permitAll()
                        .antMatchers("/employee/profile").hasAuthority(Authority.ROLE_USER.getAuthority())
                        .antMatchers("/employee/**").hasAuthority(Authority.ROLE_ADMIN.getAuthority())
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(accessDeniedHandler())
                .and()
                     // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                    .addFilterBefore(new StatelessLoginFilter(authenticationManagerBean(), tokenAuthenticationService(), failureHandler()), UsernamePasswordAuthenticationFilter.class)
                     // custom TokenStorage based authentication based on the header previously given to the client
                    .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService(), failureHandler()), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(Authority.roleHierarchyStringRepresentation());
        return roleHierarchy;
    }

    @Bean
    public RoleVoter roleVoter(RoleHierarchy roleHierarchy) {
        return new RoleHierarchyVoter(roleHierarchy);
    }

}
