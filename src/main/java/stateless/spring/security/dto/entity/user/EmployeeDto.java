package stateless.spring.security.dto.entity.user;

import stateless.spring.security.domain.Employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stateless.spring.security.enums.Competency;

@JsonPropertyOrder({ "name", "competency", "enabled" })
public class EmployeeDto {

	@JsonIgnore
	private long id;
	
    private String name;
	
	private Competency competency;

	private boolean enabled;

	public EmployeeDto(){}
	
	public EmployeeDto(Employee employee){
		this.competency = employee.getCompetency();
		this.name = employee.getName();
		this.id = employee.getId();
		this.enabled = employee.isEnabled();
	}

	
	public long getId() {
		return id;
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

	public Employee buildEmployee(){
		return new Employee(id,name, competency, enabled);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
