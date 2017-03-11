package stateless.spring.security.dto.entity.employee;

import java.util.Set;

import stateless.spring.security.domain.Credentials;
import stateless.spring.security.domain.Employee;
import stateless.spring.security.enums.Authority;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stateless.spring.security.enums.Competency;

@JsonPropertyOrder({ "name", "competency", "username", "password" })
public class CredentialsDto {
	
    private String name;
	private Competency competency;
	private String username;
	private String password;
	private Set<Authority> authorities;
	
	public CredentialsDto(){}

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

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Credentials buildEmployee(){
		return new Credentials(new Employee(name, competency), username, password, authorities);
	}

	public EmployeeDto detail(){
		EmployeeDto employee = new EmployeeDto();
		employee.setCompetency(this.getCompetency());
		employee.setName(this.getName());
		return employee;
	}
}
