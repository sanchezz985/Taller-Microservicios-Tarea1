package com.consulting.mgt.springboot.acmehrsystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.consulting.mgt.springboot.acmehrsystem.exceptions.EmployeeException;
import com.consulting.mgt.springboot.acmehrsystem.exceptions.EmployeeNotFoundException;
import com.consulting.mgt.springboot.acmehrsystem.exceptions.WorkstationException;
import com.consulting.mgt.springboot.acmehrsystem.exceptions.WorkstationNotFoundException;
import com.consulting.mgt.springboot.acmehrsystem.model.Employee;
import com.consulting.mgt.springboot.acmehrsystem.model.Workstation;
import com.consulting.mgt.springboot.acmehrsystem.repository.EmployeeRepository;
import com.consulting.mgt.springboot.acmehrsystem.service.EmployeeService;
import com.consulting.mgt.springboot.acmehrsystem.utils.HRUtils;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	// workstation url
	private static final String BASEPATHWS = "http://localhost:8081/acme-hr-system-workstation/v1/workstations";
	private static final String BYEMPLOYEEID = "/employees/";

	@SuppressWarnings("unchecked")
	@Override
	public Employee retrieveById(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		List<Workstation> workstations = new ArrayList<>();
		if (employee != null) {
			if (employee.getHasWorkstation() == 1)
				workstations = (List<Workstation>) HRUtils.sendRequest(HttpMethod.GET,
						BASEPATHWS + BYEMPLOYEEID + employee.getId(), "", ArrayList.class);
		}
		employee.setWorkstations(workstations);
		return employee;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> retrieveAll() {
		List<Employee> listEmployee = employeeRepository.findAllByOrderByIdAsc();
		for (Employee employee : listEmployee) {
			List<Workstation> workstations = new ArrayList<>();
			if (employee != null) {
				if (employee.getHasWorkstation() == 1)
					workstations = (List<Workstation>) HRUtils.sendRequest(HttpMethod.GET,
							BASEPATHWS + BYEMPLOYEEID + employee.getId(), "", ArrayList.class);
			}
			employee.setWorkstations(workstations);
		}
		return listEmployee;
	}

	@Override
	public Employee register(Employee employee) {		
		Employee registeredEmployee = employee;
		List<Workstation> workstationList = employee.getWorkstations();
		return assignWorkstationList(workstationList, registeredEmployee, true);		
	}

	@Override
	public Employee update(Employee employee) {
		Employee registeredEmployee = employeeRepository.findById(employee.getId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		registeredEmployee.setFirstName(employee.getFirstName());
		registeredEmployee.setLastName(employee.getLastName());
		List<Workstation> workstationList = employee.getWorkstations();
		return assignWorkstationList(workstationList, registeredEmployee);
	}

	@Override
	public Employee delete(Employee employee) {

		Employee registeredEmployee = employeeRepository.findById(employee.getId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		List<Workstation> registeredWorkstations = new ArrayList<>();
		if(registeredEmployee.getHasWorkstation() > 0) {
			registeredWorkstations = (ArrayList<Workstation>) HRUtils.sendRequest(HttpMethod.GET,BASEPATHWS + BYEMPLOYEEID + registeredEmployee.getId(), "", ArrayList.class);
			System.out.println(registeredWorkstations.toString());
			//employee.getWorkstations().forEach(workstation->assignWorkstation(workstation, 0));
		}
		employeeRepository.delete(registeredEmployee);
		return employee;
	}

	@Override
	public List<Employee> retrieveByFirstName(String firstName) {
		List<Employee> listEmployee = employeeRepository.findByFirstNameIgnoreCase(firstName);
		for (Employee employee : listEmployee) {
			List<Workstation> workstations = new ArrayList<>();
			if (employee != null) {
				if (employee.getHasWorkstation() == 1)
					workstations = (List<Workstation>) HRUtils.sendRequest(HttpMethod.GET,
							BASEPATHWS + BYEMPLOYEEID + employee.getId(), "", ArrayList.class);
			}
			employee.setWorkstations(workstations);
		}
		return listEmployee;
	}

	@Override
	public List<Employee> retrieveByFirstNameAndLastName(String firstName, String lastName) {
		List<Employee> listEmployee = employeeRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName,
				lastName);
		for (Employee employee : listEmployee) {
			List<Workstation> workstations = new ArrayList<>();
			if (employee != null) {
				if (employee.getHasWorkstation() == 1)
					workstations = (List<Workstation>) HRUtils.sendRequest(HttpMethod.GET,
							BASEPATHWS + BYEMPLOYEEID + employee.getId(), "", ArrayList.class);
			}
			employee.setWorkstations(workstations);
		}
		return listEmployee;
	}

	@Override
	public Employee retrieveByEmployeeNumber(String employeeNumber) {
		Employee employee = employeeRepository.findByEmployeeNumber(employeeNumber);
		List<Workstation> workstations = new ArrayList<>();
		if (employee != null) {
			if (employee.getHasWorkstation() == 1)
				workstations = (List<Workstation>) HRUtils.sendRequest(HttpMethod.GET,
						BASEPATHWS + BYEMPLOYEEID + employee.getId(), "", ArrayList.class);
		}
		employee.setWorkstations(workstations);
		return employee;
	}

	private Workstation assignWorkstation(Workstation workstation, long employeeId) {
		if (workstation.getFacilitiesSerialNumber() == null || "".equals(workstation.getFacilitiesSerialNumber()))
			workstation.setFacilitiesSerialNumber(HRUtils.nextFacilitiesSerialNumber());
		workstation.setEmployeeId(employeeId);		
		return (Workstation)HRUtils.sendRequest(HttpMethod.PUT, BASEPATHWS, HRUtils.objectToJson(workstation), Workstation.class);
	}

	private Workstation getWorkstation(long workstationId) {
		return (Workstation) HRUtils.sendRequest(HttpMethod.GET, BASEPATHWS + "/" + workstationId, "",
				Workstation.class);
	}

	private void employeeComplement(Employee employee, int hasWorkstation) {
		employee.setHasWorkstation(hasWorkstation);
		if (employee.getEmployeeNumber() == null || "".equals(employee.getEmployeeNumber()))
			employee.setEmployeeNumber(HRUtils.nextEmployeeNumber());
	}

	private Employee assignWorkstationList(List<Workstation> workstationList, Employee employee, boolean isRegister) {
		if(workstationList == null)
			workstationList = new ArrayList<>();
		List<Workstation> preRegisteredWorkstations = new ArrayList<>();
		List<Workstation> registeredWorkstations = new ArrayList<>();
		Employee registeredEmployee = null;
		Workstation registeredWorkstation = null;
		for(Workstation workstation : workstationList) {
			if(workstation.getId() > 0) {
				registeredWorkstation = getWorkstation(workstation.getId());
				if(registeredWorkstation == null)
					throw new WorkstationException("Workstation not found."); 
				if(registeredWorkstation.getEmployeeId() > 0)
					throw new WorkstationException("Workstation you tried to assign, already assigned to other Employee");
				if(!isRegister && registeredWorkstation.getEmployeeId() == employee.getId())
					throw new WorkstationException("Workstation already assigned to this Employee");
				registeredWorkstation.setEmployeeId(employee.getId());
				registeredWorkstation.setModel(workstation.getModel());
				registeredWorkstation.setVendor(workstation.getVendor());
				preRegisteredWorkstations.add(registeredWorkstation);
			} else {
				registeredWorkstation = new Workstation();
				registeredWorkstation.setEmployeeId(employee.getId());
				registeredWorkstation.setModel(workstation.getModel());
				registeredWorkstation.setVendor(workstation.getVendor());
				preRegisteredWorkstations.add(registeredWorkstation);
			}
		}
		// save employee
		if(isRegister)
			employeeComplement(employee, preRegisteredWorkstations.isEmpty() ? 0 : 1);
		registeredEmployee = employeeRepository.save(employee);
		
		// save workstations
		for(Workstation workstation: preRegisteredWorkstations)
			registeredWorkstations.add(assignWorkstation(workstation, registeredEmployee.getId()));
		
		// set workstation list
		registeredEmployee.setWorkstations(registeredWorkstations);
		
		return registeredEmployee;
	}
	
	private Employee assignWorkstationList(List<Workstation> workstationList, Employee employee) {
		return assignWorkstationList(workstationList, employee, false);
	}
	
}