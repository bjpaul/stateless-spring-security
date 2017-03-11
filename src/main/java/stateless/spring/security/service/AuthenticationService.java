package stateless.spring.security.service;

import stateless.spring.security.domain.Credentials;
import stateless.spring.security.repository.CredentialsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationService implements UserDetailsService {

    private CredentialsRepository credentialsRepository;

    public AuthenticationService(CredentialsRepository credentialsRepository){
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            if(username == null ||  username.isEmpty()){
                throw new UsernameNotFoundException("Employee not found");
            }
            Credentials credentials = credentialsRepository.findOne(username);
            if (credentials == null) {
                throw new UsernameNotFoundException("Employee not found");
            }
            return credentials;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Employee not found");
        }
    }
}
