package stateless.spring.security.dto.entity.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeCountDto {
	@JsonProperty("total_employee_count")
	private long employeeCount = 0;

	public EmployeeCountDto(){}

	public EmployeeCountDto(long count) {
		this.employeeCount = count;
	}

	public long getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}
	

}
