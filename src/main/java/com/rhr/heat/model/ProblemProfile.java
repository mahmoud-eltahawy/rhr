package com.rhr.heat.model;

import java.sql.Date;
import java.sql.Time;

import com.rhr.heat.enums.Machine;
import com.rhr.heat.enums.ShiftOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemProfile {
	private Date date;
	private ShiftOrder shift;
	private Time beginTime;
	private Time endTime;
	private Machine machine;
}
