package com.kh.myprj.domain.member.dao;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kh.myprj.domain.member.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberDAOImplTest {
	
	@Autowired
	private MemberDAO mdao;
	
	@Test
	@DisplayName("가입")
	@Disabled
	void insert() {
		
		//간단하게 dto객체 만들고 집어넣기
		MemberDTO mdto = new MemberDTO();
		
		mdto.setEmail("test2@test.com");
		mdto.setPw("1234");
		mdto.setTel("010-2222-3333");
		mdto.setNickname("테스터2");
		mdto.setGender("남");
		mdto.setRegion("울산");
		mdto.setBirth(java.sql.Date.valueOf("2021-08-20"));
		mdto.setLetter("1");
		
		log.info("member_id:{}",mdao.insert(mdto));
	}
	
	@Test
	@DisplayName("회원조회 by id")
	void findById() {
		log.info("findById:{}", mdao.findByID(24));
	}
	
	@Test
	@DisplayName("회원조회 by email")
	void findByEmail() {
		log.info("findByEmail:{}", mdao.findByEmail("test@test.com"));
	}
	
	@Test
	@DisplayName("이메일 찾기")
	void findEmail() {
		MemberDTO mdto = mdao.findByEmail("test@test.com");
		String findedEmail = mdao.findEmail(mdto.getTel(), mdto.getBirth());
		Assertions.assertThat(findedEmail).isEqualTo(mdto.getEmail());
		log.info("findedEmail:{}", findedEmail);
	}
	
	@Test
	@DisplayName("비밀번호 찾기")
	void findPw() {
		MemberDTO mdto = mdao.findByEmail("test@test.com");
		String findedPw = mdao.findPw(mdto.getEmail(), mdto.getTel(), mdto.getBirth());
		Assertions.assertThat(findedPw).isEqualTo(mdto.getPw());
		log.info("findedPw:{}", findedPw);
	}
}
