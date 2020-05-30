package com.enset.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	@GetMapping("/notAuthorize")
	public String error() {
		return "notAuthorize";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest httpRequest) throws ServletException {
		httpRequest.logout();
		return "redirect:/login";
	}

}
