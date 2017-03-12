package stateless.spring.security.dto.entity.employee;

/**
 * Created by bijoypaul on 12/03/17.
 */
public class LoginDto {

    private String username;
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if (username == null) {
            username = "";
        }
        username = username.trim();
        this.username = username;
    }
    public String getPassword() {
        if (password == null) {
            password = "";
        }
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "LoginDto [username=" + username + ", password=" + password
                + "]";
    }
}
