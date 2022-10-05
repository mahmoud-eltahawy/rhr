package com.rhr.heat.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtmsShiftStatus {
	@Id @GeneratedValue
	@Column(name = "atms_status_id")
	private Long id;
	@OneToMany
	@JoinTable(
			name = "atm1_problems",
			joinColumns = 
			@JoinColumn(name = "atm_id",referencedColumnName = "atms_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> atm1;
	@OneToMany
	@JoinTable(
			name = "atm2_problems",
			joinColumns = 
			@JoinColumn(name = "atm_id",referencedColumnName = "atms_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> atm2;
}
