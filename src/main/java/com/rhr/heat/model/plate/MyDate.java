package com.rhr.heat.model.plate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyDate {
	private Integer year;
	private Integer month;
	private Integer day;
	
	public String form() {
		String years  = this.year.toString();
		String months = null;
		String days   = null;
		
		if(this.month < 10) {
			months = "0" + this.month;
		} else {
			months = this.month.toString();
		}
		
		if(this.day < 10) {
			days = "0" + this.day;
		} else {
			days = this.day.toString();
		}
		
		return days+"/"+months+"/"+years;
	}
}