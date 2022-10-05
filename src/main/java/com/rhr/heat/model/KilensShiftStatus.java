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
public class KilensShiftStatus {
	@Id @GeneratedValue
	@Column(name = "kilens_status_id")
	private Long id;
	@OneToMany
	@JoinTable(
			name = "kilen1_problems",
			joinColumns = 
			@JoinColumn(name = "kilen_id",referencedColumnName = "kilens_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> kiln1;
	@OneToMany
	@JoinTable(
			name = "kilen2_problems",
			joinColumns = 
			@JoinColumn(name = "kilen_id",referencedColumnName = "kilens_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> kiln2;
	@OneToMany
	@JoinTable(
			name = "kilen3_problems",
			joinColumns = 
			@JoinColumn(name = "kilen_id",referencedColumnName = "kilens_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> kiln3;
	@OneToMany
	@JoinTable(
			name = "kilen4_problems",
			joinColumns = 
			@JoinColumn(name = "kilen_id",referencedColumnName = "kilens_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> kiln4;
	@OneToMany
	@JoinTable(
			name = "kilen5_problems",
			joinColumns = 
			@JoinColumn(name = "kilen_id",referencedColumnName = "kilens_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> kiln5;
}
