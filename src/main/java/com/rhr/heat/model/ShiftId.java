package com.rhr.heat.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.rhr.heat.enums.ShiftType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@IdClass(ShiftId.class)
@Data
@AllArgsConstructor
public class ShiftId implements Serializable {
	@Id
	private LocalDate date;
	@Id
	private ShiftType shift;
}
