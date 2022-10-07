package com.rhr.heat.model.plate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyTime {
	@Column(name = "hour")
	private Integer hour;
	@Column(name = "minute")
	private Integer minute;
}
