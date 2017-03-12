package stateless.spring.security.service.crypto;

import stateless.spring.security.domain.Credentials;

/**
 * Created by bijoypaul on 13/03/17.
 */
public interface PasswordResolver {

    void encode(Credentials credentials);

    boolean matches(CharSequence rawPassword, Credentials credentials);
}
