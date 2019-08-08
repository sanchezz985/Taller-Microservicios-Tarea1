package com.consulting.mgt.springboot.acmehrsystem.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consulting.mgt.springboot.acmehrsystem.exceptions.WorkstationNotFoundException;
import com.consulting.mgt.springboot.acmehrsystem.model.Workstation;
import com.consulting.mgt.springboot.acmehrsystem.repository.WorkstationRepository;
import com.consulting.mgt.springboot.acmehrsystem.service.WorkstationService;

@Service
@Transactional
public class WorkstationServiceImpl implements WorkstationService {

	@Autowired
	private WorkstationRepository workstationRepository;

	/**
	 * Retrieve workstation by ID
	 */
	@Override
	public Workstation retrieveById(Long id) {
		return workstationRepository.findById(id)
				.orElseThrow(() -> new WorkstationNotFoundException("Workstation not found"));
	}

	/**
	 * Retrieve all workstations
	 */
	@Override
	public List<Workstation> retrieveAll() {
		return workstationRepository.findAll();
	}

	/**
	 * Register a new workstation
	 */
	@Override
	public Workstation register(Workstation workstation) {
		workstation = workstationRepository.save(workstation);
		return workstation;
	}

	/**
	 * Update a selected workstation
	 */
	@Override
	public Workstation update(Workstation workstation) {
		return register(workstation);
	}

	@Override
	public Workstation delete(Workstation workstation) {
		workstationRepository.delete(workstation);
		return workstation;
	}

	@Override
	public List<Workstation> retrieveByVendor(String vendor) {
		return workstationRepository.findByVendor(vendor);
	}

	@Override
	public List<Workstation> retrieveByFacilitiesSerialNumber(String facilitiesSerialNumber) {
		return workstationRepository.findByFacilitiesSerialNumber(facilitiesSerialNumber);
	}

	@Override
	public List<Workstation> retrieveByEmployeeId(long employeeId) {
		return workstationRepository.findByEmployeeId(employeeId);
	}

}
