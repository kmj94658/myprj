package com.kh.myprj.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.myprj.web.form.LoginForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	//초기화면
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	//로그인 양식
	@GetMapping("/login")
	public String loginForm(@ModelAttribute LoginForm loginForm) {
		return "loginForm";
	}
	
	//로그인 처리(로그인 폼객체 이용)
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult) {
		log.info("LoginForm:{}", loginForm);

		if(bindingResult.hasErrors()) {
			log.info("BindingResult:{}", bindingResult);
			return "loginForm"; //로그인화면으로 리턴할때 입력했던 값 저장하려면
		}
		return "home";
	}
	
	//로그아웃
	@GetMapping("/logout") 
		public String logout() {
			return "home";
		}
	
}
