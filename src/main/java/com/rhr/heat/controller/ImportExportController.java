package com.rhr.heat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.service.ImportExportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImportExportController {
	private final ImportExportService importExportService;

	@RequestMapping("/exp/all")
	public String exportAllToFile() {
		importExportService.exportAll();
		return "exported";
	}
	
	@RequestMapping("/imp/all")
	public String importFromFile() {
		importExportService.importAll();
		return "imported";
	}
}
