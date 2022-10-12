package com.rhr.heat.controller;

import java.io.File;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.service.ImportExportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImportExportController {
	private final ImportExportService importExportService;

	@RequestMapping("/exp/all")
	public String exportAllToFile() {
		importExportService.exportThat();
		return "exported";
	}
	
	@RequestMapping("/exp/after")
	public String exportAfter(@RequestParam("date")Date date) {
		importExportService.exportAfter(new java.sql.Date(date.getTime()));
		return "exported";
	}
	
	@RequestMapping("/exp/before")
	public String exportBefore(@RequestParam("date")Date date) {
		importExportService.exportBefore(new java.sql.Date(date.getTime()));
		return "exported";
	}
	
	@RequestMapping("/exp/bet")
	public String exportBetween(
			@RequestParam("date")Date older,
			@RequestParam("date")Date newer) {
		importExportService.exportBetween(
				new java.sql.Date(older.getTime()),
				new java.sql.Date(newer.getTime()));
		return "exported";
	}
	
	@RequestMapping("/imp/all")
	public String importFromFile() {
		importExportService.importThat();
		return "imported";
	}
	
	@RequestMapping("/imp/file")
	public String importFile(@RequestParam("file")File file) {
		importExportService.importThat(file);
		return "imported";
	}
}
