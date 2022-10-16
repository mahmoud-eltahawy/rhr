package com.rhr.heat.entity;

import java.sql.Time;
import java.util.Set;
import java.util.UUID;

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetail {
	private UUID id;
	private Set<Problem> problems;
	private Machine machine;
	private Time beginTime;
	private Time endTime;
}
