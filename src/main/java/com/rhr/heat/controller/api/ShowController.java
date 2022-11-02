package com.rhr.heat.controller.api;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.entity.Shift;
import com.rhr.heat.service.api.ShowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShowController {
	private final ShowService showService;

	@GetMapping("/shift/after")
	public List<Shift> newerShifts(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		return showService.shiftsNewerThan(date);
	}

	@GetMapping("/shift/before")
	public List<Shift> olderShifts(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		return showService.shiftsOlderThan(date);
	}

	@GetMapping("/shift/day")
	public List<Shift> day(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		return showService.getDay(date);
	}

	@GetMapping("/shift/shift")
	public Shift shift(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day,
			@RequestParam("order")String order) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); //why? !!!!
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date date = new Date(cal.getTime().getTime());
		return showService.getShift(date, order);
	}
	
	@RequestMapping("/shift/bet")
	public List<Shift> exportBetween(@RequestParam("oyear")Integer oyear,@RequestParam("nyear")Integer nyear,
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
		return showService.shiftsBetween(older, newer);
	}
}
