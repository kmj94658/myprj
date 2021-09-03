package com.kh.myprj.web.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
	private Long id;				//서버 내부적으로 관리용 아이디
	private String email;			//회원 아이디
	private String nickname;
	private String role;
}