package stateless.spring.security.dto.entity.user;

import java.util.List;

public class EmployeeList {

	public List<EmployeeDto> employeeList;

	public EmployeeList(){}
	
	public EmployeeList(List<EmployeeDto> userList){
		this.employeeList = userList;
	}

	public List<EmployeeDto> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeDto> employeeList) {
		this.employeeList = employeeList;
	}
}
