package stateless.spring.security.controller;


import org.springframework.security.core.Authentication;
import stateless.spring.security.domain.Employee;
import stateless.spring.security.dto.entity.employee.CredentialsDto;
import stateless.spring.security.dto.entity.employee.EmployeeDto;
import stateless.spring.security.dto.entity.employee.EmployeeList;
import stateless.spring.security.dto.entity.employee.ProfileDto;
import stateless.spring.security.dto.response.AbstractResponseDto;
import stateless.spring.security.service.EmployeeService;
import stateless.spring.security.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/employee","/employee/"})
public class EmployeeController {
	
    @Autowired
    EmployeeService employeeService;  //Service which will do all data retrieval/manipulation work
 
    //-------------------Employee Profile Detail--------------------------------------------------------
    

    @RequestMapping(value = {"/profile", "/profile/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<AbstractResponseDto> profileDetail(Authentication authentication) {
        ProfileDto profile = employeeService.fetchProfile((Employee)authentication.getDetails());
        return ResponseUtil.success().body(profile).message("Profile detail fetched successfully !!!").send(HttpStatus.OK);
    }

    //-------------------Updating Employee Profile Detail--------------------------------------------------------

    @RequestMapping(value = {"/profile", "/profile/"}, method = RequestMethod.PUT)
    public HttpEntity<AbstractResponseDto> updateProfileDetail(Authentication authentication, @RequestBody ProfileDto profileDto) {
        ProfileDto profile = employeeService.updateProfile((Employee)authentication.getDetails(), profileDto);
        return ResponseUtil.success().body(profile).message("Profile updated successfully !!!").send(HttpStatus.OK);
    }

    //-------------------Count employee--------------------------------------------------------
    
    @RequestMapping(value = {"/count","/count/"}, method = RequestMethod.GET)
    public HttpEntity<AbstractResponseDto> employeeCout() {
    	
        return ResponseUtil.success().body(employeeService.count()).message("Employee count fetched successfully !!!").send(HttpStatus.OK);
    }
    
    //-------------------Retrieve All Users--------------------------------------------------------
     
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<AbstractResponseDto> listAllEmployees() {
        EmployeeList employeeList = employeeService.findAllEmployees();
        if(employeeList.getEmployeeList().isEmpty()){
            return ResponseUtil.error().message("No data found").send(HttpStatus.NOT_FOUND);
        }
        return ResponseUtil.success().body(employeeList).message("Employee list fetched successfully !!!").send(HttpStatus.OK);
    }
 
 
    //-------------------Retrieve Single Employee--------------------------------------------------------
     
    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.GET)
    public ResponseEntity<AbstractResponseDto> getUser(@PathVariable("id") long id) {
        EmployeeDto employee = employeeService.findById(id);
        if (employee == null) {
        	return ResponseUtil.error().message("No data found").send(HttpStatus.NOT_FOUND);
        }
        return ResponseUtil.success().body(employee).message("Employee fetched successfully !!!").send(HttpStatus.OK);
    }
 
     
     
    //-------------------Create an Employee--------------------------------------------------------
     
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AbstractResponseDto> createEmployee(@RequestBody CredentialsDto employee) {
 
        employeeService.saveEmployee(employee);
 
        return ResponseUtil.success().body(employee.detail()).message("Employee created successfully !!!").send(HttpStatus.CREATED);
    }

    //------------------- Enable an Employee --------------------------------------------------------

    @RequestMapping(value = {"/{id}/enable", "/{id}/enable/"}, method = RequestMethod.PUT)
    public ResponseEntity<AbstractResponseDto> enableEmployee(@PathVariable("id") long id) {

        EmployeeDto employee = employeeService.findById(id);
        if (employee==null) {
            return ResponseUtil.error().message("Employee with id " + id + " not found").send(HttpStatus.NOT_FOUND);
        }
        employee = employeeService.enableOrDisableEmployee(employee, true);
        return ResponseUtil.success().body(employee).message("Employee enabled successfully !!!").send(HttpStatus.OK);
    }

    //------------------- Disable an Employee --------------------------------------------------------

    @RequestMapping(value = {"/{id}/disable", "/{id}/disable/"}, method = RequestMethod.PUT)
    public ResponseEntity<AbstractResponseDto> disableEmployee(@PathVariable("id") long id) {

        EmployeeDto employee = employeeService.findById(id);
        if (employee==null) {
            return ResponseUtil.error().message("Employee with id " + id + " not found").send(HttpStatus.NOT_FOUND);
        }
        employee = employeeService.enableOrDisableEmployee(employee, false);

        return ResponseUtil.success().body(employee).message("Employee disabled successfully !!!").send(HttpStatus.OK);
    }

    //------------------- Update an Employee --------------------------------------------------------
     
    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.PUT)
    public ResponseEntity<AbstractResponseDto> updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeDto employee) {
         
        EmployeeDto current = employeeService.findById(id);
         
        if (current==null) {
            return ResponseUtil.error().message("Employee with id " + id + " not found").send(HttpStatus.NOT_FOUND);
        }
        
        employeeService.updateEmployee(current, employee);
        return ResponseUtil.success().body(current).message("Employee updated successfully !!!").send(HttpStatus.OK);
    }
 
    //------------------- Delete an Employee --------------------------------------------------------
     
    @RequestMapping(value = {"/{id}", "/{id}/"}, method = RequestMethod.DELETE)
    public ResponseEntity<AbstractResponseDto> deleteEmployee(@PathVariable("id") long id) {
 
        EmployeeDto employee = employeeService.findById(id);
        if (employee == null) {
            System.out.println("Unable to delete. Employee with id " + id + " not found");
            return ResponseUtil.error().message("Unable to delete. Employee with id " + id + " not found").send(HttpStatus.NOT_FOUND);
        }
 
        employeeService.deleteUserById(id);
        return ResponseUtil.success().body(employee).message("Employee deleted successfully !!!").send(HttpStatus.OK);
    }
 
     
    //------------------- Delete All Employees --------------------------------------------------------
     
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<AbstractResponseDto> deleteAllEmployees() {
 
        employeeService.deleteAllEmployees();
        return ResponseUtil.success().body(new EmployeeList()).message("All Employee deleted successfully !!!").send(HttpStatus.OK);
    }
}
