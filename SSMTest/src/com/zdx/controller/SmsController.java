package com.zdx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdx.Util.sendsms;

@Controller
public class SmsController {
	
	@RequestMapping("/sendSms")
	@ResponseBody
	public String sendSms(@RequestParam("phone")String phone){
		System.out.println(phone);
		return sendsms.sendDX(phone.trim());
	}
	
}
