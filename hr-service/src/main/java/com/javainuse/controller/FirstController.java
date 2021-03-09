package com.javainuse.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class FirstController {

	@GetMapping("/message/{name}/name")
	public String test(@PathVariable String name) {
		return String.format("Hello %s JavaInUse Called in First Service",name);
	}
}
