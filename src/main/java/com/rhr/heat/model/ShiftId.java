package com.rhr.heat.model;

import java.sql.Date;

import com.rhr.heat.enums.ShiftOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftId {
	private Long id;
	private Date date;
	private ShiftOrder shift;
}