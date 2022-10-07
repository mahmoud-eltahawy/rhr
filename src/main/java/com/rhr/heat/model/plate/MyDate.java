package com.rhr.heat.model.plate;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Data;

@IdClass(MyDate.class)
@Entity
@Data
@AllArgsConstructor
public class MyDate implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer year;
	@Id
	private Integer Month;
	@Id
	private Integer day;
}