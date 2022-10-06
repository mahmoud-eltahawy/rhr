package com.rhr.heat.controller;

import org.springframework.web.bind.annotation.RestController;

import com.rhr.heat.service.Tester;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	private final Tester tester;
	
}
