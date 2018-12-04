package com.yhz.webmagic.data_analysis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value = "/hello")
	@ResponseBody
	public String test(){
		return "Hello World!";
	}
}
