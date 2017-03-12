package stateless.spring.security.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by bijoypaul on 11/03/17.
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static String REALM="Restful Spring Security Application";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + REALM + "\"");
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + exception.getMessage());
    }
}
