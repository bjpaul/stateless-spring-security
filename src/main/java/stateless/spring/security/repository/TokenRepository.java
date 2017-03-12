package stateless.spring.security.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stateless.spring.security.domain.token.TokenStorage;

/**
 * Created by bijoypaul on 12/03/17.
 */
@Repository
public interface TokenRepository extends CrudRepository<TokenStorage, String> {

    @Modifying(clearAutomatically = true)
    @Query("update TokenStorage ts set ts.expired = true where ts.credentials.username =:username")
    void invalidateAllUserToken(@Param("username") String username);
}
