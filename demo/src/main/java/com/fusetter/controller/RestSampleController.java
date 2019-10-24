package com.fusetter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/items2")
public class RestSampleController {

	@GetMapping
    String getItems() {
		System.out.println("REST TEST2");

        return "Rest Test";
    }

}
