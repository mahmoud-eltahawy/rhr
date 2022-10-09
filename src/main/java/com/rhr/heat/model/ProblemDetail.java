package com.rhr.heat.model;

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.model.plate.MyTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetail {
	private Problem problem;
	private Machine machine;
	private MyTime beginTime;
	private MyTime endTime;
}
