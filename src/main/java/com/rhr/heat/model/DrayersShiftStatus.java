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
public class DrayersShiftStatus {
	@Id @GeneratedValue
	private Long id;
	@OneToOne
	private MachineStatus drayer1;
	@OneToOne
	private MachineStatus drayer2;
	@OneToOne
	private MachineStatus drayer3;
	@OneToOne
	private MachineStatus drayer4;
	@OneToOne
	private MachineStatus drayer5;
	@OneToOne
	private MachineStatus drayer6;
	@OneToOne
	private MachineStatus drayer7;
}
