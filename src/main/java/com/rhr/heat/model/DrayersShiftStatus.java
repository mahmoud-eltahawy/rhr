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
public class DrayersShiftStatus {
	@Id @GeneratedValue
	@Column(name = "drayers_status_id")
	private Long id;
	@OneToMany
	@JoinTable(
			name = "drayer1_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer1;
	@OneToMany
	@JoinTable(
			name = "drayer2_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer2;
	@OneToMany
	@JoinTable(
			name = "drayer3_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer3;
	@OneToMany
	@JoinTable(
			name = "drayer4_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer4;
	@OneToMany
	@JoinTable(
			name = "drayer5_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer5;
	@OneToMany
	@JoinTable(
			name = "drayer6_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer6;
	@OneToMany
	@JoinTable(
			name = "drayer7_problems",
			joinColumns = 
			@JoinColumn(name = "drayer_id",referencedColumnName = "drayers_status_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> drayer7;
}
