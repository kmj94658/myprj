package com.kh.myprj.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.myprj.domain.member.dto.MemberDTO;
import com.kh.myprj.domain.member.svc.MemberSVC;
import com.kh.myprj.web.form.LoginForm;
import com.kh.myprj.web.form.LoginMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final MemberSVC memberSVC;

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
	
	//로그인 처리
	@PostMapping("/login")
	public String login(
			@Valid @ModelAttribute LoginForm loginForm, 
			BindingResult bindingResult,
			Model model, HttpServletRequest request) {
		
		log.info("LoginForm:{}",loginForm);
		
		MemberDTO memberDTO = memberSVC.islogin(loginForm.getEmail(), loginForm.getPw());
		
		if(memberDTO == null) {
			bindingResult.reject("error.login", "회원정보가 없습니다");
		}
		
		
//		//계정확인
//		if("user@test.com".equals(loginForm.getEmail()) && "user1234".equals(loginForm.getPw())) {
//			loginMember = new LoginMember("user","회원","user");
//		}else if("admin@test.com".equals(loginForm.getEmail()) && "admin1234".equals(loginForm.getPw())){
//			loginMember = new LoginMember("admin","관리자","admin");
//		}else {
//			//글로벌 오류 추가
//			bindingResult.reject("loginChk", "아이디 또는 비밀번호가 잘못되었습니다");			
//		}
		
		//글로벌오류 체크
		if(bindingResult.hasErrors()) {
			log.info("BindingResult:{}",bindingResult);
			return "loginForm";
		}
		
		//세션생성
		//세션정보가 없으면 새로 생성
		HttpSession session =request.getSession(true);
		LoginMember loginMember = new LoginMember(memberDTO.getId(), memberDTO.getEmail(), memberDTO.getNickname(), "회원");		
		session.setAttribute("loginMember", loginMember );
		
		return "redirect:/";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		//세션정보가 있으면 그걸 받아오기(없어도 새로 생성하지 않는다)
		HttpSession session = request.getSession(false);
		//세션제거
		if(session != null) {
			session.invalidate(); //디폴트설정이 30분이라 지나면 자동 로그아웃된다
		}
		
		return "home";
	}
}