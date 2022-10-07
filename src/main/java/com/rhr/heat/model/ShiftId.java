package com.rhr.heat.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.joda.time.LocalDate;

import com.rhr.heat.enums.ShiftType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class ShiftId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "shift")
	private ShiftType shift;
}
