package com.rhr.heat.controller.api;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.entity.topLayer.Shift;
import com.rhr.heat.enums.ShiftOrder;
import com.rhr.heat.model.Day;
import com.rhr.heat.service.api.ShowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShowController {
	private final ShowService service;
	
	@PostMapping("/get/emp/week")
	public TreeMap<String,Day> lastEmpWeek(
			@RequestParam("idx")Integer idx,
			@RequestParam("name")String username) {
		return service.pickLastEmployeeSections(username, idx);
	}
	
	@PostMapping("/get/week")
	public TreeMap<String,Day> lastWeek(@RequestParam("idx")Integer idx) {
		return service.pickLastWeeks(idx);
	}
	
	@PostMapping("/get/day")
	public TreeMap<String,Day> getDay(
			@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		return service.findDay(new Date(getDate(year, month, day).getTime()));
	}

	@PostMapping("/shift/id")
	public UUID getShiftId(
			@RequestParam("order")String order,
			@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(getDate(year, month, day).getTime());
		return service.getShiftId(date, ShiftOrder.valueOf(order));
	}

	@GetMapping("/shift/after")
	public List<Shift> newerShifts(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(getDate(year, month, day).getTime());
		return service.shiftsNewerThan(date);
	}

	@GetMapping("/shift/before")
	public List<Shift> olderShifts(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(getDate(year, month, day).getTime());
		return service.shiftsOlderThan(date);
	}

	@GetMapping("/shift/day")
	public List<Shift> day(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day) {
		Date date = new Date(getDate(year, month, day).getTime());
		return service.getDay(date);
	}

	@GetMapping("/shift/shift")
	public Shift shift(@RequestParam("year")Integer year,
			@RequestParam("month")Integer month,
			@RequestParam("day")Integer day,
			@RequestParam("order")String order) {
		Date date = new Date(getDate(year, month, day).getTime());
		return service.getShift(date, order);
	}
	
	@RequestMapping("/shift/bet")
	public List<Shift> exportBetween(
		@RequestParam("oyear")Integer oyear,
		@RequestParam("nyear")Integer nyear,
		@RequestParam("omonth")Integer omonth,
		@RequestParam("nmonth")Integer nmonth,
		@RequestParam("oday")Integer oday,
		@RequestParam("nday")Integer nday) {
		Date older = new Date(getDate(oyear, omonth, oday).getTime());
		Date newer = new Date(getDate(nyear, nmonth, nday).getTime());
		return service.shiftsBetween(older, newer);
	}

	private java.util.Date getDate(Integer year,Integer month, Integer day){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		//TODO:  why i should substract one !!!!
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
}
