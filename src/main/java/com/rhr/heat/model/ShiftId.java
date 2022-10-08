package com.rhr.heat.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.rhr.heat.enums.ShiftType;
import com.rhr.heat.model.plate.MyDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@IdClass(ShiftId.class)
@Data
@AllArgsConstructor
public class ShiftId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private MyDate date;
	@Id
	@Enumerated(EnumType.STRING)
	private ShiftType shift;
}