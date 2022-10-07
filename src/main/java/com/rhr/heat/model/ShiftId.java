package com.rhr.heat.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.joda.time.LocalDate;

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
