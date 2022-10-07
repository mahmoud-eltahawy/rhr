package com.rhr.heat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.LocalTime;

import com.rhr.heat.enums.AtmsCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalFlow {
	@Id @GeneratedValue
	@Column(name = "flow_id")
	private Long id;
	private AtmsCase atmsCase;
	private Integer minFlow;
	private Integer maxFlow;
	private LocalTime caseBeginTime;
	private LocalTime caseEndTime;
}