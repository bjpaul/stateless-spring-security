package stateless.spring.security.service.token;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bijoypaul on 11/03/17.
 */
public abstract class TokenAuthenticationService {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final String AUTH_HEADER_NAME = "MY-CUSTOM-TOKEN";

    protected abstract String generateToken(Authentication authentication);

    protected abstract Authentication verifyToken(String token);

    // Adding the custom token as HTTP header to the response
    public void addAuthentication(HttpServletResponse response, Authentication authentication) {
        response.addHeader(AUTH_HEADER_NAME, generateToken(authentication));
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if(token == null){
            token = response.getHeader(AUTH_HEADER_NAME);
        }
        if (token != null) {
            return verifyToken(token);
        }
        return null;
    }

}
