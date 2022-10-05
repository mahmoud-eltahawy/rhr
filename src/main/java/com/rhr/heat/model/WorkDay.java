package com.rhr.heat.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private Long id;
	private Date date;
	@OneToOne
	private Shift shift1;
	@OneToOne
	private Shift shift2;
	@OneToOne
	private Shift shift3;
}
