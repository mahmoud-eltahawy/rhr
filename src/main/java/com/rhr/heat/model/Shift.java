package com.rhr.heat.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
	@Id @GeneratedValue
	private Long id;
	@OneToOne
	private KilensShiftStatus kilens;
	@OneToOne
	private DrayersShiftStatus drayers;
	@OneToOne
	private AtmsShiftStatus atms;
	@OneToMany
	private List<ProblemDetail> project;
	private Integer minTemperature;
	private Integer maxTemperature;
	@OneToMany
	private List<Employee> employees;
	private String exceptionalNote;
}
