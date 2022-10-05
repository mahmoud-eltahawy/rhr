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
public class KilensShiftStatus {
	@Id @GeneratedValue
	private Long id;
	@OneToOne
	private MachineStatus kiln1;
	@OneToOne
	private MachineStatus kiln2;
	@OneToOne
	private MachineStatus kiln3;
	@OneToOne
	private MachineStatus kiln4;
	@OneToOne
	private MachineStatus kiln5;
}
