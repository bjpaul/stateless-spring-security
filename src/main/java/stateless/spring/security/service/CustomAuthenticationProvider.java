package stateless.spring.security.service;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import stateless.spring.security.domain.Credentials;
import stateless.spring.security.domain.Employee;
import stateless.spring.security.repository.CredentialsRepository;

/**
 * Created by bijoypaul on 12/03/17.
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private CredentialsRepository credentialsRepository;

    public CustomAuthenticationProvider(CredentialsRepository credentialsRepository){
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                messages.getMessage(
                        "CustomAuthenticationProvider.onlySupports",
                        "Only UsernamePasswordAuthenticationToken is supported"));

        // Determine username
        String username = (authentication.getPrincipal() == null) ? ""
                : authentication.getName().trim();

        // Determine password
        String password = (authentication.getCredentials() == null) ? ""
                : authentication.getCredentials().toString();

        try {
            Credentials credentials = credentialsRepository.findOne(username);
            if (credentials == null || !credentials.getPassword().equals(password)) {
                throw new BadCredentialsException(messages.getMessage(
                        "CustomAuthenticationProvider.authenticate", "Bad credentials"));
            }

            Employee employee = credentials.getEmployee();
            if(employee == null || !employee.isEnabled()){
                throw new DisabledException(messages.getMessage(
                        "CustomAuthenticationProvider.authenticate", "Employee is disabled"));
            }

            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    username, password,credentials.getAuthorities());
            result.setDetails(employee);

            return result;
            
        } catch (Exception e) {
            throw new BadCredentialsException(messages.getMessage(
                    "CustomAuthenticationProvider.authenticate", "Bad credentials"));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
