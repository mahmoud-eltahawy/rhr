package com.rhr.heat.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.rhr.heat.enums.AtmsCase;
import com.rhr.heat.model.plate.MyTime;

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
	@Enumerated(EnumType.STRING)
	private AtmsCase atmsCase;
	private Integer minFlow;
	private Integer maxFlow;
	@AttributeOverrides({
		@AttributeOverride(name = "hour" , column = @Column(name = "begin_hour")),
		@AttributeOverride(name = "minute" , column = @Column(name = "begin_minute"))
	})
	@Embedded
	private MyTime caseBeginTime;
	@AttributeOverrides({
		@AttributeOverride(name = "hour" , column = @Column(name = "end_hour")),
		@AttributeOverride(name = "minute" , column = @Column(name = "end_minute"))
	})
	@Embedded
	private MyTime caseEndTime;
}