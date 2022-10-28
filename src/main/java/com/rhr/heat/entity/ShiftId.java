package com.rhr.heat.entity;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

import com.rhr.heat.enums.ShiftOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftId {
	private UUID id;
	private Date date;
	private ShiftOrder shift;
}
