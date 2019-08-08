package com.consulting.mgt.springboot.acmehrsystem.service;

import java.util.List;

import com.consulting.mgt.springboot.acmehrsystem.model.Workstation;

public interface WorkstationService {

	Workstation retrieveById(Long id);

	List<Workstation> retrieveAll();

	Workstation register(Workstation workstation);

	Workstation update(Workstation workstation);

	Workstation delete(Workstation workstation);

	List<Workstation> retrieveByVendor(String vendor);

	List<Workstation> retrieveByFacilitiesSerialNumber(String facilitiesSerialNumber);

	List<Workstation> retrieveByEmployeeId(long employeeId);
}
