package com.rhr.heat.model;

import com.rhr.heat.enums.ShiftType;
import com.rhr.heat.model.plate.MyDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftId {
	private MyDate date;
	private ShiftType shift;
}
