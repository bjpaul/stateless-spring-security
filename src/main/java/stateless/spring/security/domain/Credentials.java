package stateless.spring.security.domain;

import org.springframework.security.core.userdetails.UserDetails;
import stateless.spring.security.enums.Authority;

import java.util.Collection;

import javax.persistence.*;

@Entity
public class Credentials implements UserDetails {

    @Id
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(
            name = "credentials_with_role",
            joinColumns = @JoinColumn(name = "username")
    )
    @Enumerated(EnumType.STRING)
    private Collection<Authority> authorities;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Credentials() {
    }

    public Credentials(Employee employee, String username, String password, Collection<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }


    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return employee.isEnabled();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    @Override
    public String toString() {
        return "Credentials [username=" + username + ", password=" + password + "]";
    }


}
