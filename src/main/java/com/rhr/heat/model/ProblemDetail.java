package com.rhr.heat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.LocalTime;

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetail {
	@Id @GeneratedValue
	@Column(name = "problem_id")
	private Long id;
	private Problem problem;
	private LocalTime beginTime;
	private LocalTime endTime;
	private Machine machine;
}
