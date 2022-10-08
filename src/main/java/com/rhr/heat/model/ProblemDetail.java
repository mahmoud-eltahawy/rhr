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

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.Problem;
import com.rhr.heat.model.plate.MyTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetail {
	@Id @GeneratedValue
	@Column(name = "problem_id")
	private Long id;
	@Enumerated(EnumType.STRING)
	private Problem problem;
	@AttributeOverrides({
		@AttributeOverride(name = "hour" , column = @Column(name = "begin_hour")),
		@AttributeOverride(name = "minute" , column = @Column(name = "begin_minute"))
	})
	@Embedded
	private MyTime beginTime;
	@AttributeOverrides({
		@AttributeOverride(name = "hour" , column = @Column(name = "end_hour")),
		@AttributeOverride(name = "minute" , column = @Column(name = "end_minute"))
	})
	@Embedded
	private MyTime endTime;
	@Enumerated(EnumType.STRING)
	private Machine machine;
}
