package com.kh.myprj.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.myprj.domain.member.svc.MemberSVC;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/help")
@AllArgsConstructor
public class HelpController {

	private MemberSVC memberSVC;
	
	@GetMapping("/")
	public String help() {
		return "help/help";
	}
	
	//회원 아이디 찾기
	@GetMapping("/findId")
	public String findId() {
		return "help/findIdForm";
	}
	
	//회원 비밀번호 찾기
	@GetMapping("/findPw")
	public String findPw() {
		return "help/findPwForm";
	}
}
