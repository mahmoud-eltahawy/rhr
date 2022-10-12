package com.rhr.heat.model;

import java.sql.Time;
import java.util.List;

import com.rhr.heat.enums.Machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalFlow {
	private Long id;
	private List<Machine> suspendedMachines;
	private Integer minFlow;
	private Integer maxFlow;
	private Time caseBeginTime;
	private Time caseEndTime;
}