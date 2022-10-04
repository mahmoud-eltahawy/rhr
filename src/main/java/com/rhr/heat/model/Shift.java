package com.rhr.heat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
	private MachineStatus kiln1;
	private MachineStatus kiln2;
	private MachineStatus kiln3;
	private MachineStatus kiln4;
	private MachineStatus kiln5;
	private MachineStatus drayer1;
	private MachineStatus drayer2;
	private MachineStatus drayer3;
	private MachineStatus drayer4;
	private MachineStatus drayer5;
	private MachineStatus drayer6;
	private MachineStatus drayer7;
	private MachineStatus atm1;
	private MachineStatus atm2;
	private MachineStatus project;
	private Integer minTemperature;
	private Integer maxTemperature;
	private Employee employee;
}
