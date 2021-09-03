package com.kh.myprj.web;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.myprj.domain.board.dto.BoardDTO;
import com.kh.myprj.domain.board.svc.BoardSVC;
import com.kh.myprj.domain.common.dao.CodeDAO;
import com.kh.myprj.web.api.JsonResult;
import com.kh.myprj.web.form.Code;
import com.kh.myprj.web.form.LoginMember;
import com.kh.myprj.web.form.bbs.EditForm;
import com.kh.myprj.web.form.bbs.ReplyForm;
import com.kh.myprj.web.form.bbs.WriteForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BoardController {

	private final BoardSVC boardSVC;
	private final CodeDAO codeDAO;
	
	@ModelAttribute("category") //뷰단에서 category로 접근 가능
	public List<Code> category(){
		List<Code> list = codeDAO.getCode("A05");
		log.info("code-category:{}",list);
		return list;
	}
	
	//원글 작성 양식
	@GetMapping("/")
	public String writeForm(Model model, HttpServletRequest request) {
		
		//작성 전 로그인 됐는지 확인
		WriteForm writeForm = new WriteForm();
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
			writeForm.setBid(loginMember.getId());
			writeForm.setBemail(loginMember.getEmail());
			writeForm.setBnickname(loginMember.getNickname());
			
		}
		model.addAttribute("writeForm", writeForm);
		
		return "bbs/writeForm";
	}
	
	//원글 작성 처리
	@PostMapping("/")
	public String write(@Valid @ModelAttribute WriteForm writeForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) { //writeForm객체와 연결, 폼객체 바인딩하면서 에러는 bindingResult에 담는다
		
		//오류가 있으면
		if(bindingResult.hasErrors()) {
			return "bbs/writeForm";
		}
		//정상로직
		BoardDTO boardDTO = new BoardDTO();
		
		BeanUtils.copyProperties(writeForm, boardDTO); //필드명이 같으면 copyProperties
		Long bnum = boardSVC.write(boardDTO);
		
		redirectAttributes.addAttribute("bnum", bnum);
		
		return "redirect:/bbs/{bnum}";
	}
	
	//답글 작성 양식
	@GetMapping("/reply")
	public String replyForm(@ModelAttribute ReplyForm replyForm) {
		return "bbs/writeForm";
	}
	
	//답글 작성 처리
	@PostMapping("/reply")
	public String reply(@Valid @ModelAttribute ReplyForm replyForm, BindingResult bindingResult) { //writeForm객체와 연결, 폼객체 바인딩하면서 에러는 bindingResult에 담는다
		
		//오류가 있으면
		if(bindingResult.hasErrors()) {
			return "bbs/replyForm";
		}
		//정상로직
		
		
		return "redirect:/bbs/list";
	}
	
	//게시글 상세
	@GetMapping("/{bnum}")
	public String detailItem(@PathVariable("bnum") Long bnum, Model model) { //백에서 불러온것을 뷰단에서 참고할수 있게 model 사용
		
		model.addAttribute("detailItem", boardSVC.itemDetail(bnum)) ;
		
		return "bbs/detailItem";
	}
	
	//게시글 목록
	@GetMapping("/list")
	public String list(Model model) {
		List<BoardDTO> list = boardSVC.list();
		model.addAttribute("list", list);
		return "bbs/list";
	}
	
	//게시글 수정 양식
	@GetMapping("/{bnum}/edit")
	public String editForm(@PathVariable Long bnum, Model model) {
		model.addAttribute("item", boardSVC.itemDetail(bnum));
		return "bbs/editForm";
	}
	
	//게시글 수정 처리
	@PatchMapping("/{bnum}/edit")
	public String edit(@PathVariable Long bnum, @Valid @ModelAttribute EditForm editForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "bbs/editForm";
		}
		
		return "redirect:/bbs/{bnum}";
	}
	
	//게시글 삭제
	@DeleteMapping("/{bnum}")
	@ResponseBody
	public JsonResult<String> delItem(@PathVariable Long bnum) {
		
		boardSVC.delItem(bnum);
		return new JsonResult<String>("00","ok",String.valueOf(bnum));
		
	}
}
