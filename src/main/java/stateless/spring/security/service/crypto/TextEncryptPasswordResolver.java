package stateless.spring.security.service.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import stateless.spring.security.domain.Credentials;

/**
 * Created by bijoypaul on 13/03/17.
 */
public class TextEncryptPasswordResolver implements PasswordResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String secretKey;

    public TextEncryptPasswordResolver(String secretKey){
        this.secretKey = secretKey;
    }

    @Override
    public void encode(Credentials credentials) {
        // generates a random 8-byte salt that is then hex-encoded
        String rawPassword = credentials.getPassword();
        String salt = KeyGenerators.string().generateKey();
        String encodedPassword = Encryptors.text(secretKey, salt).encrypt(rawPassword);
        logger.debug("Encode => raw password: "+ rawPassword+", salt: "+salt+", encoded password: "+encodedPassword);
        credentials.setSalt(salt);
        credentials.setPassword(encodedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, Credentials credentials) {
        String salt = credentials.getSalt();
        String encodedPassword = credentials.getPassword();
        String decodedPassword = Encryptors.text(secretKey, salt).decrypt(credentials.getPassword());
        logger.debug("Decode => encoded password: "+ encodedPassword+", salt: "+salt+", decode password: "+decodedPassword);
        return rawPassword.equals(decodedPassword);
    }
}
