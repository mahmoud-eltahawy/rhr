package com.rhr.heat.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Day {
	private Shift shiftOne;
	private Shift shiftTwo;
	private Shift shiftThree;
	private Date date;
}
