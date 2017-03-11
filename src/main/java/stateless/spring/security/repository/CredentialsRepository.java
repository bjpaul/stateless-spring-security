package stateless.spring.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stateless.spring.security.domain.Credentials;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, String>{
}
