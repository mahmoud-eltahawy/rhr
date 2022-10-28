package com.rhr.heat.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

	@Bean
	public File dataFolder() {
		File home = new File(System.getProperty("user.home")+File.separator+"rhrData");
		home.mkdir();
		return home;
	}
}
