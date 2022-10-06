package com.rhr.heat.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.rhr.heat.dao.ShiftType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@IdClass(ShiftId.class)
@Data
@AllArgsConstructor
public class ShiftId implements Serializable {
	@Id
	private Date date;
	@Id
	private ShiftType shift;
}
