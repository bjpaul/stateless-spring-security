package stateless.spring.security.service;

import java.util.ArrayList;
import java.util.List;

import stateless.spring.security.domain.Credentials;
import stateless.spring.security.domain.Employee;
import stateless.spring.security.dto.entity.employee.*;
import stateless.spring.security.repository.CredentialsRepository;
import stateless.spring.security.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CredentialsRepository credentialsRepository;

	public ProfileDto fetchProfile(Credentials credentials){
		return new ProfileDto(credentials);
	}

	public ProfileDto updateProfile(Credentials credentials, ProfileDto profileDto){
		credentials.setUsername(profileDto.getUsername());
		credentials.setPassword(profileDto.getPassword());
		Employee employee = credentials.getEmployee();
		employee.setCompetency(profileDto.getCompetency());
		employee.setName(profileDto.getName());
		credentialsRepository.save(credentials);
		return new ProfileDto(credentials);
	}

	public EmployeeCountDto count() {
		return new EmployeeCountDto(employeeRepository.count());
	}
	
	public EmployeeList findAllEmployees() {
		Iterable<Employee> employees = employeeRepository.findAll();
		
		if(employees == null){
			return new EmployeeList();
		}
		
		List<EmployeeDto> dtos = new ArrayList<EmployeeDto>();
		for (Employee employee : employees) {
			dtos.add(new EmployeeDto(employee));
		}
		return new EmployeeList(dtos);
	}

	public EmployeeDto findById(long id) {
		Employee employee = employeeRepository.findOne(id);
		EmployeeDto employeeDto = null;
		if(employee != null){
			employeeDto = new EmployeeDto(employee);
		}
		return employeeDto;
	}

	public void saveEmployee(CredentialsDto credentialsDto) {
		credentialsRepository.save(credentialsDto.buildEmployee());
	}

	public void updateEmployee(EmployeeDto current, EmployeeDto employee) {
		current.setName(employee.getName());
        current.setCompetency(employee.getCompetency());
        employeeRepository.save(current.buildEmployee());
	}

	public EmployeeDto enableOrDisableEmployee(EmployeeDto employeeDto, boolean isEnable) {
		employeeDto.setEnabled(isEnable);
		employeeRepository.save(employeeDto.buildEmployee());
		return employeeDto;
	}

	public void deleteUserById(long id) {
		employeeRepository.delete(id);
	}

	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}

}
