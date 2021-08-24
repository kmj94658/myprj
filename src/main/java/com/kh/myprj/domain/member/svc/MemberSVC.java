package com.kh.myprj.domain.member.svc;

import java.sql.Date;

import com.kh.myprj.domain.member.dto.MemberDTO;

public interface MemberSVC {
	/**
	 * 가입
	 * @param memberDTO
	 * @return
	 */
	void join(MemberDTO memberDTO);
	
	/**
	 * 해당 이메일 회원존재유무 체크
	 * @param email
	 * @return
	 */
	boolean isExistEmail(String email);
	
	/**
	 * 로그인 체크
	 * @param id
	 * @param pw
	 * @return
	 */
	MemberDTO islogin(String email, String pw);
	
	/**
	 * 수정
	 * @param id
	 * @param memberDTO
	 */
	void update(long id, MemberDTO memberDTO);
	
	/**
	 * id로 회원 삭제
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * email로 회원 삭제
	 * @param email
	 */
	void delete(String email);
	
	/**
	 * 이메일 찾기
	 * @param tel
	 * @param birth
	 * @return 이메일
	 */
	String findEmail(String tel, Date birth);
	
	/**
	 * 비밀번호 찾기
	 * @param email
	 * @param tel
	 * @param birth
	 * @return
	 */
	String findPw(String email, String tel, Date birth);
}