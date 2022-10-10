package com.rhr.heat.model;

import java.sql.Time;

import com.rhr.heat.enums.AtmsCase;

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
	private Time caseBeginTime;
	private Time caseEndTime;
}