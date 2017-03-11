package stateless.spring.security.dto.entity.employee;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stateless.spring.security.domain.Credentials;
import stateless.spring.security.enums.Competency;

/**
 * Created by bijoypaul on 11/03/17.
 */
@JsonPropertyOrder({ "name", "competency", "username", "password" })
public class ProfileDto {

    private String name;

    private Competency competency;

    private String username;

    private String password;

    public ProfileDto(){}

    public ProfileDto(Credentials credentials){
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.name = credentials.getEmployee().getName();
        this.competency = credentials.getEmployee().getCompetency();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Competency getCompetency() {
        return competency;
    }

    public void setCompetency(Competency competency) {
        this.competency = competency;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
                ", name='" + name + '\'' +
                ", competency=" + competency +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
