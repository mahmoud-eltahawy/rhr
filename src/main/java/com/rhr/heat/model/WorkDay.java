package com.rhr.heat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDay {
	@Id @GeneratedValue
	@Column(name = "workday_id")
	private Long id;
	private Date date;
	@OneToOne
	@JoinTable(
			name = "day_shift1",
			joinColumns = 
			@JoinColumn(name = "workday_id",referencedColumnName = "workday_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"))
	private Shift shift1;
	@OneToOne
	@JoinTable(
			name = "day_shift2",
			joinColumns = 
			@JoinColumn(name = "workday_id",referencedColumnName = "workday_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"))
	private Shift shift2;
	@OneToOne
	@JoinTable(
			name = "day_shift3",
			joinColumns = 
			@JoinColumn(name = "workday_id",referencedColumnName = "workday_id"),
			inverseJoinColumns = 
			@JoinColumn(name = "shift_id",referencedColumnName = "shift_id"))
	private Shift shift3;
}
