package stateless.spring.security.service.token;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import stateless.spring.security.domain.Credentials;
import stateless.spring.security.domain.Employee;
import stateless.spring.security.domain.token.TokenStorage;
import stateless.spring.security.repository.CredentialsRepository;
import stateless.spring.security.repository.TokenRepository;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bijoypaul on 12/03/17.
 */
public class PersistentTokenService extends TokenAuthenticationService {

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final long VALID_DURATION;
    private final CredentialsRepository credentialsRepository;
    private final TokenRepository tokenRepository;

    public PersistentTokenService(CredentialsRepository credentialsRepository, TokenRepository tokenRepository, long validity) {
        this.credentialsRepository = credentialsRepository;
        this.tokenRepository = tokenRepository;
        this.VALID_DURATION = 1000 * 60 * 60 * 24 * validity;
    }

    @Override
    protected String generateToken(Authentication authentication) {
        Credentials credentials = credentialsRepository.findOne(authentication.getName());
        String token = UUID.randomUUID().toString();

        logger.debug("Storing token into local cache: "+token+", for user: "+authentication.getName());
        tokenRepository.save(new TokenStorage(token, credentials, System.currentTimeMillis()+VALID_DURATION));

        return token;
    }

    @Override
    protected Authentication verifyToken(String token) {

        TokenStorage tokenStorage = tokenRepository.findOne(token);
        if (tokenStorage != null) {

            if(tokenStorage.isExpired()){
                throw new CredentialsExpiredException(messages.getMessage(
                        "PersistentTokenService.verifyToken", "Token expired"));
            }

            if(new Date().getTime() < tokenStorage.getValidity()) {

                Credentials credentials = tokenStorage.getCredentials();
                Employee employee = credentials.getEmployee();

                if (!employee.isEnabled()) {
                    throw new DisabledException(messages.getMessage(
                            "PersistentTokenService.verifyToken", "Employee is disabled"));
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), credentials.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(employee);
                logger.debug("Fetching credentials : " + token + ", for user: " + usernamePasswordAuthenticationToken.getName());

                return usernamePasswordAuthenticationToken;
            }else {

                tokenStorage.setExpired(true);
                tokenRepository.save(tokenStorage);

                throw new CredentialsExpiredException(messages.getMessage(
                        "PersistentTokenService.verifyToken", "Token expired"));
            }
        }
        return null;
    }

}
