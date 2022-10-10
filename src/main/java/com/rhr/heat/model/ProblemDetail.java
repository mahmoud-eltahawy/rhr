package com.rhr.heat.model;

import java.sql.Time;

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetail {
	private Long id;
	private Problem problem;
	private Machine machine;
	private Time beginTime;
	private Time endTime;
}
