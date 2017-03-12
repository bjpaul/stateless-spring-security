package stateless.spring.security.service.token;

import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Created by bijoypaul on 11/03/17.
 */

public class SimpleTokenService extends TokenAuthenticationService{

    public static Map<String, Authentication> tokenStore = new HashMap<>();

    @Override
    protected String generateToken(Authentication authentication) {
        String token = UUID.randomUUID().toString();
        logger.debug("Storing token into local cache: "+token+", for user: "+authentication.getName());
        tokenStore.put(token, authentication);
        return token;
    }

    @Override
    protected Authentication verifyToken(String token) {
        Authentication authentication = tokenStore.get(token);
        if(authentication != null){
            logger.debug("Fetching credentials from local cache: "+token+", for user: "+authentication.getName());
        }
        return authentication;
    }

}
