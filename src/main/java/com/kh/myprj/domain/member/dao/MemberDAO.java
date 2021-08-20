package com.kh.myprj.domain.member.dao;

import com.kh.myprj.domain.member.dto.MemberDTO;

public interface MemberDAO {

	/**
	 * 가입
	 * @param memberDTO
	 * @return
	 */
	long insert(MemberDTO memberDTO);
	
	/**
	 * 조회 by id
	 * @param id
	 * @return
	 */
	MemberDTO findByID(long id);
	
	/**
	 * 조회 by email
	 * @param email
	 * @return
	 */
	MemberDTO findByEmail(String email);

}