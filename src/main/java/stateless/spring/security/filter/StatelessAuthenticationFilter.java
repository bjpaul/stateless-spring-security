package stateless.spring.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.GenericFilterBean;

import stateless.spring.security.service.token.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;
	private final AuthenticationFailureHandler failureHandler;

	public StatelessAuthenticationFilter(TokenAuthenticationService taService, AuthenticationFailureHandler failureHandler) {
		this.tokenAuthenticationService = taService;
		this.failureHandler = failureHandler;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		try {
			SecurityContextHolder.getContext().setAuthentication(
					tokenAuthenticationService.getAuthentication((HttpServletRequest) req, (HttpServletResponse) res));
			chain.doFilter(req, res); // always continue
		}catch (AuthenticationException e){
			SecurityContextHolder.clearContext();
			failureHandler.onAuthenticationFailure((HttpServletRequest) req, (HttpServletResponse) res, e);
		}

	}
}