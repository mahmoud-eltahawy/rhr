package com.rhr.heat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtmsShiftStatus {
	@Id @GeneratedValue
	private Long id;
	@OneToOne
	private MachineStatus atm1;
	@OneToOne
	private MachineStatus atm2;
}
