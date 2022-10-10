package com.rhr.heat.model;

import java.sql.Date;
import java.util.List;

import com.rhr.heat.enums.ShiftType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
	private Date date;
	private ShiftType shift;
	private List<ProblemDetail> problems;
	private List<Employee> employees;
	private List<TotalFlow> totalFlowAverage;
	private String exceptionalNote;
	private Integer minTemperature;
	private Integer maxTemperature;
}