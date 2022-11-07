package com.rhr.heat.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperature {
	private UUID id;
	private Machine machine;
	private Integer max;
	private Integer min;
}
