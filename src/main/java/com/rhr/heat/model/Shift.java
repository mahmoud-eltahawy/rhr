package com.rhr.heat.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	@Column(name = "shift_id")
	private Long id;
	@OneToOne
	@JoinTable(
			name = "shift_kilens",
			joinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "kilens_status_id",referencedColumnName = "kilens_status_id"))
	private KilensShiftStatus kilens;
	@OneToOne
	@JoinTable(
			name = "shift_drayers",
			joinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "drayers_status_id",referencedColumnName = "drayers_status_id"))
	private DrayersShiftStatus drayers;
	@OneToOne
	@JoinTable(
			name = "shift_atms",
			joinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "atms_status_id",referencedColumnName = "atms_status_id"))
	private AtmsShiftStatus atms;
	@OneToMany
	@JoinTable(
			name = "project_problems",
			joinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> project;
	@OneToMany
	@JoinTable(
			name = "shift_employees",
			joinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "employee_id",referencedColumnName = "employee_id"))
	private List<Employee> employees;
	@OneToMany
	@JoinTable(
			name = "shift_total_flow",
			joinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "flow_id",referencedColumnName = "flow_id"))
	private List<TotalFlow> totalFlowAverage;
	private String exceptionalNote;
	private Integer minTemperature;
	private Integer maxTemperature;
}
