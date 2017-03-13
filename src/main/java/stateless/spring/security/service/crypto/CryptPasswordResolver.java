package stateless.spring.security.service.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;
import stateless.spring.security.domain.Credentials;

/**
 * Created by bijoypaul on 13/03/17.
 */
public class CryptPasswordResolver implements PasswordResolver {

    private PasswordEncoder passwordEncoder;

    public CryptPasswordResolver(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void encode(Credentials credentials) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
    }

    @Override
    public boolean matches(CharSequence rawPassword, Credentials credentials) {
        return passwordEncoder.matches(rawPassword, credentials.getPassword());
    }
}
