package com.kh.myprj.web;

import java.io.IOException;
import java.util.ArrayList;
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
import com.kh.myprj.domain.common.dto.MetaOfUploadFile;
import com.kh.myprj.domain.common.dto.UpLoadFileDTO;
import com.kh.myprj.domain.common.file.FileStore;
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
	private final FileStore fileStore;
	
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
	public String write(@Valid @ModelAttribute WriteForm writeForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException { //writeForm객체와 연결, 폼객체 바인딩하면서 에러는 bindingResult에 담는다
		
		//오류가 있으면
		if(bindingResult.hasErrors()) {
			return "bbs/writeForm";
		}
		//정상로직
		BoardDTO boardDTO = new BoardDTO();
		
		BeanUtils.copyProperties(writeForm, boardDTO); //필드명이 같으면 copyProperties
		
		//첨부파일 파일시스템에 저장 후 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(writeForm.getFiles());
		//UploadFileDTO 변환
		boardDTO.setFiles(convert(storedFiles));
		
		Long bnum = boardSVC.write(boardDTO);
		
		redirectAttributes.addAttribute("bnum", bnum);
		
		return "redirect:/bbs/{bnum}";
	}
	
	private UpLoadFileDTO convert(MetaOfUploadFile attatchFile) {
		UpLoadFileDTO uploadFileDTO = new UpLoadFileDTO();
		BeanUtils.copyProperties(attatchFile, uploadFileDTO);
		return uploadFileDTO;
	}
	
	private List<UpLoadFileDTO> convert(List<MetaOfUploadFile> uploadFiles) {
		List<UpLoadFileDTO> list = new ArrayList<>();
	
		for(MetaOfUploadFile file : uploadFiles) {
			UpLoadFileDTO uploadFIleDTO = convert(file);
			list.add( uploadFIleDTO );
		}		
		return list;
	}
	
	//답글 작성 양식
	@GetMapping("/reply/{bnum}")
	public String replyForm(@PathVariable Long bnum, Model model, HttpServletRequest request) {
		
		ReplyForm replyForm = new ReplyForm();
		
		//세션에서 회원 id, email, nickname 가져오기
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("loginMember") != null) {
			LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
			replyForm.setBid(loginMember.getId());
			replyForm.setBemail(loginMember.getEmail());
			replyForm.setBnickname(loginMember.getNickname());
			
		}
		
		BoardDTO parentBoardDTO = boardSVC.itemDetail(bnum);
		
		//부모의 글번호 가져오기
		replyForm.setPbnum(parentBoardDTO.getBnum());
		//bcategory는 부모의 카테고리 가져오기
		replyForm.setBcategory(parentBoardDTO.getBcategory());
		//btitle은 부모의 제목 가져오기 (컨트롤러에서 해줘도 되고 뷰단에서 해도 되고)
		replyForm.setBtitle("답글 : "+parentBoardDTO.getBtitle());
		
		model.addAttribute("replyForm",	replyForm);
		return "bbs/replyForm";
	}
	
	//답글 작성 처리
	@PostMapping("/reply/{bnum}") //부모글번호
	public String reply(
			@PathVariable("bnum") Long pbnum, 
			@Valid @ModelAttribute ReplyForm replyForm, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes) 
					throws IllegalStateException, IOException { //writeForm객체와 연결, 폼객체 바인딩하면서 에러는 bindingResult에 담는다
		
		//오류가 있으면
		if(bindingResult.hasErrors()) {
			return "bbs/replyForm";
		}
		//정상로직
		BoardDTO boardDTO = new BoardDTO();
		
		BeanUtils.copyProperties(replyForm, boardDTO); //필드명이 같으면 copyProperties
		
		//부모글의 pbnum, bgroup, bstep, bindent 를 boardDTO에 채워줘야 한다
		BoardDTO pboardDTO = boardSVC.itemDetail(pbnum);
		boardDTO.setPbnum(pboardDTO.getBnum());
		boardDTO.setBgroup(pboardDTO.getBgroup());
		boardDTO.setBstep(pboardDTO.getBstep());
		boardDTO.setBindent(pboardDTO.getBindent());
		
		//첨부파일 파일시스템에 저장 후 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(replyForm.getFiles());
		//UploadFileDTO 변환
		boardDTO.setFiles(convert(storedFiles));
		
		Long rbnum = boardSVC.reply(boardDTO);
		
		
		redirectAttributes.addAttribute("bnum", rbnum);
		return "redirect:/bbs/{bnum}";
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
		
		model.addAttribute("editForm", boardSVC.itemDetail(bnum));
		return "bbs/editForm";
	}
	
	//게시글 수정 처리
	@PatchMapping("/{bnum}/edit")
	public String edit(
			@PathVariable Long bnum,
			@Valid @ModelAttribute EditForm editForm,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors()) {
			log.info("게시글수정처리오류:{}",bindingResult);
			return "bbs/editForm";
		}
		
		BoardDTO boardDTO = new BoardDTO();
		
		//첨부파일 파일시스템에 저장후 메타정보 추출
		List<MetaOfUploadFile> storedFiles = fileStore.storeFiles(editForm.getFiles());
		//UploadFileDTO 변환
		boardDTO.setFiles(convert(storedFiles));		
		BeanUtils.copyProperties(editForm, boardDTO);
		
		Long modifyedBnum = boardSVC.modifyItem(bnum, boardDTO);
		redirectAttributes.addAttribute("bnum", modifyedBnum);
		
		return "redirect:/bbs/{bnum}";
	}
	
}
