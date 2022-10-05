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
public class KilensShiftStatus {
	@Id @GeneratedValue
	private Long id;
	@OneToMany
	private List<ProblemDetail> kiln1;
	@OneToMany
	private List<ProblemDetail> kiln2;
	@OneToMany
	private List<ProblemDetail> kiln3;
	@OneToMany
	private List<ProblemDetail> kiln4;
	@OneToMany
	private List<ProblemDetail> kiln5;
}
