package com.consulting.mgt.springboot.acmehrsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workstation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String vendor;

	private String model;

	private String facilitiesSerialNumber;

	private long employeeId;
}
