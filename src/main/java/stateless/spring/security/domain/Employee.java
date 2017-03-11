package stateless.spring.security.domain;

import stateless.spring.security.enums.Competency;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String name;

    @Enumerated(EnumType.STRING)
	private Competency competency;

	private boolean enabled;

	public Employee(){}
	
	public Employee(String name, Competency competency){
		this.name = name;
		this.competency = competency;
	}
	
	public Employee(Long id, String name, Competency competency, boolean enabled) {
		this.id = id;
		this.name = name;
		this.competency = competency;
		this.enabled = enabled;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", competency=" + competency + "]";
	}
	
	
}
