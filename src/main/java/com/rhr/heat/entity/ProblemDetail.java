package com.rhr.heat.entity;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetail {
	private UUID id;
	private List<Problem> problems;
	private Machine machine;
	private Time beginTime;
	private Time endTime;
}
