package com.kh.myprj.web;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.myprj.domain.member.svc.MemberSVC;
import com.kh.myprj.web.api.FindEmailReq;
import com.kh.myprj.web.api.FindPwReq;
import com.kh.myprj.web.api.JsonResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Controller
@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
public class ApiMemberController {
	
	private final MemberSVC memberSVC;
	
	//회원가입시 이메일 중복체크
	@GetMapping("/email/dupChk")
	public JsonResult<String> dupChkEmail(@RequestParam String email) {
		
		JsonResult<String> result = null;
		if(memberSVC.isExistEmail(email)) {
			result = new JsonResult<String> ("00","ok", email);
		}else {
			result = new JsonResult<String> ("01","nok", null);
		}
		return result;
	}
	
	//이메일 찾기
	@PostMapping("/email")
	//@ResponseBody restController로 대신 사용가능
	public JsonResult<String> findEmail(@Valid @RequestBody FindEmailReq findEmailReq, BindingResult bindingResult) { //필드이름 같게 해서 findEmailReq와 바인딩. 요청 메세지의 바디를 읽어서 바인딩 시킨다.
		
		if(bindingResult.hasErrors()) {
			return null;
		}
		
		String findedEmail = memberSVC.findEmail(findEmailReq.getTel(), findEmailReq.getBirth());
		
		return new JsonResult<String>("00","ok",findedEmail);
		
	}
	
	//비밀번호 찾기
	@PostMapping("/pw")
	public Object findPw(@Valid @RequestBody FindPwReq findPwReq, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return bindingResult;
		}
		
		String findedPw = memberSVC.findPw(findPwReq.getEmail(), findPwReq.getTel(), findPwReq.getBirth());
		
		return new JsonResult<String>("00","ok",findedPw);
	}
}





