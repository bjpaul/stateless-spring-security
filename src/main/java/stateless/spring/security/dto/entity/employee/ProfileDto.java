package stateless.spring.security.dto.entity.employee;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import stateless.spring.security.domain.Employee;
import stateless.spring.security.enums.Competency;

/**
 * Created by bijoypaul on 11/03/17.
 */
@JsonPropertyOrder({ "name", "competency", "username", "password" })
public class ProfileDto {

    private String name;

    private Competency competency;

    public ProfileDto(){}

    public ProfileDto(Employee employee){
        this.name = employee.getName();
        this.competency = employee.getCompetency();
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


    @Override
    public String toString() {
        return "ProfileDto{" +
                ", name='" + name + '\'' +
                ", competency=" + competency +
                '}';
    }
}
