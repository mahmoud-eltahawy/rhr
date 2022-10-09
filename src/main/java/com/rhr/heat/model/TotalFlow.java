package com.rhr.heat.model;

import com.rhr.heat.enums.AtmsCase;
import com.rhr.heat.model.plate.MyTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalFlow {
	private Long id;
	private AtmsCase atmsCase;
	private Integer minFlow;
	private Integer maxFlow;
	private MyTime caseBeginTime;
	private MyTime caseEndTime;
}