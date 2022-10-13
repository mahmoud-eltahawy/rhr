package com.rhr.heat.controller;

import java.io.File;
import java.sql.Date;
import java.util.Calendar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.service.ImportExportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/io")
public class ImportExportController {
	private final ImportExportService importExportService;

	@RequestMapping("/xall")
	public String exportAllToFile() {
		importExportService.exportThat();
		return "exported";
	}
	
	@RequestMapping("/xafter")
	public String exportAfter(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		importExportService.exportAfter(date);
		return "exported";
	}
	
	@RequestMapping("/xbefore")
	public String exportBefore(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		importExportService.exportBefore(date);
		return "exported";
	}
	
	@RequestMapping("/xbetween")
	public String exportBetween(
			@RequestParam("older")Date older,
			@RequestParam("newer")Date newer) {
		importExportService.exportBetween(
				new java.sql.Date(older.getTime()),
				new java.sql.Date(newer.getTime()));
		return "exported";
	}
	
	@RequestMapping("/iall")
	public String importFromFile() {
		importExportService.importThat();
		return "imported";
	}
	
	@RequestMapping("/ifile")
	public String importFile(@RequestParam("file")File file) {
		importExportService.importThat(file);
		return "imported";
	}
}
