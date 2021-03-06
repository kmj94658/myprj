package com.kh.myprj.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.myprj.domain.common.dao.CodeDAO;
import com.kh.myprj.domain.member.dto.MemberDTO;
import com.kh.myprj.domain.member.svc.MemberSVC;
import com.kh.myprj.web.form.Code;
import com.kh.myprj.web.form.LoginMember;
import com.kh.myprj.web.form.member.ChangePwForm;
import com.kh.myprj.web.form.member.EditForm;
import com.kh.myprj.web.form.member.JoinForm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberSVC memberSVC;
	private final CodeDAO codeDAO;
	
	@ModelAttribute("hobby")
	public List<Code> hobby(){
		List<Code> list = codeDAO.getCode("A01");
		log.info("code-hobby:{}",list);
		return list;
	}
	@ModelAttribute("gender")
	public List<Code> gender(){
		List<Code> list = codeDAO.getCode("A02");
		log.info("code-gender:{}",list);
		return list;
	}
	@ModelAttribute("region")
	public List<Code> region(){
		List<Code> list = codeDAO.getCode("A03");
		log.info("code-region:{}",list);
		return list;
	}
	
	/**
	 * 회원가입양식
	 * @return
	 */
	@GetMapping("/join")
	public String joinForm(Model model) {
		log.info("회원가입양식 호출됨!");
		model.addAttribute("joinForm", new JoinForm());
		return "members/joinForm";
	}
	/**
	 * 회원가입처리
	 * @return
	 */
	@PostMapping("/join")
	public String join(@Valid @ModelAttribute JoinForm joinForm, BindingResult bindingResult) {
		log.info("회원가입처리 호출됨!");
		log.info("joinForm:{}",joinForm);
		
		//비밀번호, 비밀번호체크 일치 체크
		if(!joinForm.getPw().equals(joinForm.getPwChk())) {
			bindingResult.reject("error.member.join", "비밀번호가 다릅니다");
			return "members/joinForm";
		}
		
		//회원 존재 유무
		if(memberSVC.isExistEmail(joinForm.getEmail())) {
			bindingResult.reject("error.member.join", "동일한 이메일이 존재합니다");
			return "members/joinForm";
		}
		
		if(bindingResult.hasErrors()) {
			log.info("errors={}",bindingResult);
			return "members/joinForm";
		}
		
		MemberDTO mdto = new MemberDTO();
		BeanUtils.copyProperties(joinForm, mdto, "letter");
		mdto.setLetter(joinForm.isLetter() ? "1" : "0");
		memberSVC.join(mdto);
		return "redirect:/login";
	}
	/**
	 * 회원수정양식
	 * @return
	 */
	@GetMapping("/edit")
	public String editForm(HttpServletRequest request, Model model) {
		log.info("회원수정양식 호출됨!");
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		//세션이 없으면 로그인페이지로 이동
		if(loginMember == null) return "redirect:/login";
		
		//회원정보 가져오기
		MemberDTO memberDTO = memberSVC.findByEmail(loginMember.getEmail());
		EditForm editForm = new EditForm();
		BeanUtils.copyProperties(memberDTO, editForm);
		
		//뉴스레터 정보 수정하기
		if (memberDTO.getLetter().equals("1")) {
			editForm.setLetter(true); 
		} else {
			editForm.setLetter(false);
		}
		
		//가져와서 뷰에 넘기려면 모델 필요
		model.addAttribute("editForm", editForm);
		
		return "mypage/memberEditForm";
	}
	/**
	 * 회원수정처리
	 * @return
	 */
	@PatchMapping("/edit")
	public String edit(@ModelAttribute EditForm editForm, BindingResult bindingResult, HttpServletRequest request) {
		log.info("회원수정처리 호출됨");
		HttpSession session = request.getSession(false);
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		
		//세션이 없으면 로그인페이지로 이동
		if(loginMember == null) return "redirect:/login";
		
		//회원정보 수정에서 비밀번호를 잘못 입력했을 경우
		if(!memberSVC.isMember(loginMember.getEmail(), editForm.getPw())) {
			bindingResult.rejectValue("pw", "error.member.editForm", "비밀번호가 잘못 입력되었습니다");
		}
		
		//글로벌 오류 체크
		if(bindingResult.hasErrors()) {
			log.info("errors:{}", bindingResult);
			return "members/edit";
		}
		
		MemberDTO mdto = new MemberDTO();
		BeanUtils.copyProperties(editForm, mdto);
		mdto.setLetter(editForm.isLetter() ? "1" : "0");
		
		memberSVC.update(loginMember.getId(), mdto);
		return "redirect:/members/edit";
	}
	/**
	 * 회원조회
	 * @return
	 */
	@GetMapping("/{id:.+}")
	public String view(@PathVariable("id") String id) {
		log.info("회원조회 호출됨");
		log.info("회원:{}",id);
		return "members/view";
	}

	/**
	 * 회원 탈퇴 양식
	 */
	@GetMapping("/out")
	public String outForm() {
		log.info("회원탈퇴 양식 호출됨");
		
		return "mypage/memberOutForm";
	}
	
	
	/**
	 * 회원탈퇴처리
	 * @return
	 */
	@DeleteMapping("/out")
	public String out(@RequestParam String pw, HttpServletRequest request, Model model /*BindingResult bindingResult*/) {
		log.info("회원 탈퇴 양식 호출됨");
		
		//폼이 없어 바인딩객체 이용할 수 없어서 map을 이용해서 직접 만들기 
		Map<String, String> errors = new HashMap<>();
		
		//pw값을 입력안했을때
		if(pw == null || pw.trim().length() == 0) {
			//bindingResult.rejectValue(pw, "error.member.outForm","비밀번호를 입력하세요");
			errors.put("pw", "비밀번호를 입력하세요");
			model.addAttribute("errors", errors);
			
			return "mypage/memberOutForm";
		}
		
		//세션이 없으면 로그인 페이지로
		HttpSession session = request.getSession(false);
		if(session == null) {
			return "redirect:/login";
		}
		
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		
		//회원 존재 유무 확인
		if(memberSVC.isMember(loginMember.getEmail(), pw)) {
			//true면 탈퇴
			memberSVC.outMember(loginMember.getEmail(), pw);
		} else {
			//아니면 비밀번호 틀린것
			errors.put("global", "비밀번호가 틀렸습니다");
			model.addAttribute("errors", errors);
		}
		
		//맵객체에 오류가 있으면
		if(!errors.isEmpty()) {
			return "mypage/memberOutForm";
		}
		
		//세션제거
		session.invalidate();
		
		return "home";
	}
	
	/**
	 * 회원목록
	 * @return
	 */
	@GetMapping("")
	public String listAll() {
		log.info("회원목록 호출됨");
		return "members/list";
	}
	
	//비밀번호 변경 화면
	@GetMapping("/pw")
	public String changePwForm(Model model) {
		model.addAttribute("changePwForm", new ChangePwForm());
		
		return "mypage/changePwForm";
	}
	
	//비밀번호 변경 처리
	@PatchMapping("/pw")
	public String changePw(@Valid @ModelAttribute ChangePwForm changePwForm, BindingResult bindingResult, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		if(session == null) return "redirect:/";
		
		//변경할 비밀번호 체크
		if(!changePwForm.getPostPw().equals(changePwForm.getPostPwChk())) {
			bindingResult.reject("error.member.changePw", "변경할 비밀번호가 일치하지 않습니다");
		}
		//변경할 비밀번호가 이전 비밀번호와 동일한지 체크
		if(changePwForm.getPrePw().equals(changePwForm.getPostPw())) {
			bindingResult.reject("error.member.changePw", "이전 비밀번호와 동일합니다");
		}
		
		if(bindingResult.hasErrors()) {
			return "mypage/changePwForm";
		}
		
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		boolean result = memberSVC.changePw(loginMember.getEmail(), changePwForm.getPrePw(), changePwForm.getPostPw());
		if(result) {
			session.invalidate();
			return"redirect:/login";
		}
		bindingResult.reject("error.member.changePw", "비밀번호 변경 실패");
		
		return "redirect:/members/pw";
	}
}