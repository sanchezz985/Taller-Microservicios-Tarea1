package com.consulting.mgt.springboot.acmehrsystem.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consulting.mgt.springboot.acmehrsystem.model.Employee;
import com.consulting.mgt.springboot.acmehrsystem.service.EmployeeService;

import lombok.Setter;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

	@Autowired
	private @Setter EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployee() {
		return employeeService.retrieveAll();
	}

	@GetMapping("/{employeeId}")
	public Employee getEmployee(@PathVariable long employeeId) {
		return employeeService.retrieveById(employeeId);
	}

	@GetMapping("/search/findByFirstName")
	public List<Employee> getEmployeeByFirstName(@RequestParam(required = true) String firstName) {
		return employeeService.retrieveByFirstName(firstName);
	}

	@GetMapping("/search/findByFirstNameAndLastName")
	public List<Employee> getEmployeeByFirstNameAndLastName(@RequestParam(required = true) String firstName,
			@RequestParam(required = true) String lastName) {
		return employeeService.retrieveByFirstNameAndLastName(firstName, lastName);
	}

	@GetMapping("/search/findByEmployeeNumber")
	public Employee getEmployeeByEmployeeNumber(@RequestParam(required = true) String employeeNumber) {
		return employeeService.retrieveByEmployeeNumber(employeeNumber);
	}

	@PostMapping
	public Employee postEmployee(@RequestBody Employee employee) {
		return employeeService.register(employee);
	}

	@PutMapping("/{employeeId}")
	public Employee putEmployee(@PathVariable long employeeId, @RequestBody Employee employee) {

		employee.setId(employeeId);

		return employeeService.update(employee);
	}

	@DeleteMapping("/{employeeId}")
	public Employee deleteEmployee(@PathVariable long employeeId) {
		return employeeService.delete(employeeService.retrieveById(employeeId));
	}

}