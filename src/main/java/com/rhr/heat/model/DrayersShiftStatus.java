package com.rhr.heat.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrayersShiftStatus {
	@Id @GeneratedValue
	private Long id;
	@OneToMany
	private List<ProblemDetail> drayer1;
	@OneToMany
	private List<ProblemDetail> drayer2;
	@OneToMany
	private List<ProblemDetail> drayer3;
	@OneToMany
	private List<ProblemDetail> drayer4;
	@OneToMany
	private List<ProblemDetail> drayer5;
	@OneToMany
	private List<ProblemDetail> drayer6;
	@OneToMany
	private List<ProblemDetail> drayer7;
}
