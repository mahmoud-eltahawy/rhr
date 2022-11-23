package com.rhr.heat.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class commonConfigs {

	@Bean
	public Map<String,File> dataFiles() {
		File home = new File(System.getProperty("user.home")+File.separator+"rhrData");
		home.mkdir();
		
		File allShifts = new File(home.getAbsolutePath()+File.separator+"allShifts.json");
		
		try {
			allShifts.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String,File> map = new HashMap<>();
		map.put("home", home);
		map.put("allShifts", allShifts);
		
		return map;
	}
}
