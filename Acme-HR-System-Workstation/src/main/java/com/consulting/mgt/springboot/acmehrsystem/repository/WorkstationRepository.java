package com.consulting.mgt.springboot.acmehrsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consulting.mgt.springboot.acmehrsystem.model.Workstation;

public interface WorkstationRepository extends JpaRepository<Workstation, Long> {

	List<Workstation> findByVendor(String vendor);

	List<Workstation> findByFacilitiesSerialNumber(String facilitiesSerialNumber);

	List<Workstation> findByEmployeeId(long employeeId);
	
}
