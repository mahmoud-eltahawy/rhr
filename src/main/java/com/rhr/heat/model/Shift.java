package com.rhr.heat.model;

import java.util.List;

import javax.persistence.Entity;
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
public class Shift {
	@Id
	private ShiftId shiftId;
	@OneToMany
	@JoinTable(
			name = "shift_problems",
			joinColumns ={ 
			@JoinColumn(name = "shift",referencedColumnName = "shift"),
			@JoinColumn(name = "date",referencedColumnName = "date")
			},
			inverseJoinColumns = 
			@JoinColumn(name = "problem_id",referencedColumnName = "problem_id"))
	private List<ProblemDetail> problems;
	@OneToMany
	@JoinTable(
			name = "shift_employees",
			joinColumns ={ 
			@JoinColumn(name = "shift",referencedColumnName = "shift"),
			@JoinColumn(name = "date",referencedColumnName = "date")
			},
			inverseJoinColumns = 
			@JoinColumn(name = "employee_id",referencedColumnName = "employee_id"))
	private List<Employee> employees;
	@OneToMany
	@JoinTable(
			name = "shift_total_flow",
			joinColumns ={ 
			@JoinColumn(name = "shift",referencedColumnName = "shift"),
			@JoinColumn(name = "date",referencedColumnName = "date")
			},
			inverseJoinColumns = 
			@JoinColumn(name = "flow_id",referencedColumnName = "flow_id"))
	private List<TotalFlow> totalFlowAverage;
	private String exceptionalNote;
	private Integer minTemperature;
	private Integer maxTemperature;
}
