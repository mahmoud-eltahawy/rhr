package com.rhr.heat.controller.api;

import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.GF;
import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.service.api.IOService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/io")
public class IOJsonController {
	private final IOService service;

	@GetMapping("/xall")
	public List<Shift> exportAll() {
		return service.all();
	}
	
	@GetMapping("/xafter")
	public List<Shift> exportAfter(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(GF.getDate(year, month, day).getTime());
		return service.after(date);
	}
	
	@GetMapping("/xbefore")
	public List<Shift> exportBefore(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(GF.getDate(year, month, day).getTime());
		return service.before(date);
	}

	@GetMapping("/xday")
	public List<Shift> day(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(GF.getDate(year, month, day).getTime());
		return service.day(date);
	}

	@GetMapping("/xshift")
	public List<Shift> shift(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day,
			@RequestParam("order")String order) {
		Date date = new Date(GF.getDate(year, month, day).getTime());
		return service.shift(date, order);
	}

	@GetMapping("/xshift/last")
	public List<Shift> lastShift(@RequestParam("num")int num) {
		return service.lastShift(num);
	}
	
	@GetMapping("/xbetween")
	public List<Shift> exportBetween(@RequestParam("oyear")Integer oyear,@RequestParam("nyear")Integer nyear,
			@RequestParam("omonth")Integer omonth,@RequestParam("nmonth")Integer nmonth,
			@RequestParam("oday")Integer oday,@RequestParam("nday")Integer nday) {
		Date older = new Date(GF.getDate(oyear, omonth, oday).getTime());
		Date newer = new Date(GF.getDate(nyear, nmonth, nday).getTime());
		return service.between(older,newer);
	}
	
	@PostMapping("/import")
	public int importFromFile(@RequestParam("content")String content) {
		return service.importShifts(content);
	}
}
