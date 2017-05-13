package stateless.spring.security.domain.token;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import stateless.spring.security.domain.Credentials;

import javax.persistence.*;

/**
 * Created by bijoypaul on 12/03/17.
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="token_storage")
public class TokenStorage {
    @Id
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "username")
    private Credentials credentials;

    private long validity;

    private boolean expired;

    public TokenStorage() {
    }

    public TokenStorage(String token, Credentials credentials, long validity) {
        this.token = token;
        this.credentials = credentials;
        this.validity = validity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public long getValidity() {
        return validity;
    }

    public void setValidity(long validity) {
        this.validity = validity;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
