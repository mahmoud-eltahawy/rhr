package com.rhr.heat.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import com.rhr.heat.enums.Problem;
import com.rhr.heat.enums.ShiftOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineProfile {
	private UUID id;
	private Date date;
	private ShiftOrder shift;
	private Time beginTime;
	private Time endTime;
	private List<Problem> problems;
}
