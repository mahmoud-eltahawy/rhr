package com.rhr.heat.model.plate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyTime {
	private Integer hour;
	private Integer minute;
	private String theM;
	
	public String form() {
		String minutes   = null;
		
		if(this.minute < 10) {
			minutes = "0" + this.minute;
		} else {
			minutes = this.minute.toString();
		}
		
		return this.hour+":"+minutes+" "+this.theM;
	}
}