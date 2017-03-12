package stateless.spring.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import stateless.spring.security.dto.entity.employee.LoginDto;
import stateless.spring.security.service.token.TokenAuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by bijoypaul on 11/03/17.
 */
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;

    public StatelessLoginFilter(AuthenticationManager authenticationManager, TokenAuthenticationService tokenAuthenticationService, AuthenticationFailureHandler failureHandler) {
        super(new AntPathRequestMatcher("/login", "POST"));
        setAuthenticationManager(authenticationManager);
        setAuthenticationFailureHandler(failureHandler);
        setAuthenticationSuccessHandler(new ForwardAuthenticationSuccessHandler("/employee/profile"));
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        final LoginDto credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword());

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {

        // Add the custom token as HTTP header to the response
        tokenAuthenticationService.addAuthentication(response, authentication);

        super.successfulAuthentication(request,response,chain,authentication);
    }
}
