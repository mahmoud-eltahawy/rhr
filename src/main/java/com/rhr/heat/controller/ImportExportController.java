package com.rhr.heat.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rhr.heat.service.ImportExportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/io")
public class ImportExportController {
	private final ImportExportService importExportService;

	@GetMapping("/xall")
	public String exportAllToFile() {
		importExportService.exportThat();
		return "exported";
	}
	
	@GetMapping("/xafter")
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
	
	@GetMapping("/xbefore")
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

	@GetMapping("/xday")
	public String day(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		importExportService.exportDay(date);
		return "exported";
	}

	@GetMapping("/xshift")
	public String shift(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day,
			@RequestParam("order")String order) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		importExportService.exportShift(date, order);
		return "exported";
	}
	
	@GetMapping("/xbetween")
	public String exportBetween(@RequestParam("oyear")Integer oyear,@RequestParam("nyear")Integer nyear,
			@RequestParam("omonth")Integer omonth,@RequestParam("nmonth")Integer nmonth,
			@RequestParam("oday")Integer oday,@RequestParam("nday")Integer nday) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, oyear);
		cal.set(Calendar.MONTH, omonth - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, oday);
		Date older = new Date(cal.getTime().getTime());
		cal.set(Calendar.YEAR, nyear);
		cal.set(Calendar.MONTH, nmonth - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, nday);
		Date newer = new Date(cal.getTime().getTime());
		importExportService.exportBetween(older,newer);
		return "exported";
	}
	
	@GetMapping("/iall")
	public String importFromFile() {
		importExportService.importThat();
		return "imported";
	}
	
	@GetMapping("/ifile")
	public String importFile(@RequestParam("file")MultipartFile file) {
		File f = new File(System.getProperty("user.home")+File.separator+"drhrfile.json");
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		importExportService.importThat(f);
		return "imported";
	}
}
