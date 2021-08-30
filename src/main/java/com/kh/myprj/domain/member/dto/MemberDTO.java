package com.kh.myprj.domain.member.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberDTO {
	
	private long id;							//id        number(8),
  private String email;					//email     varchar2(40),
  private String pw;						//pw        varchar2(10) constraint member_pw_nn not null,
  private String tel;						//tel       varchar2(13),
  private String nickname;			//nickname  varchar2(30),
  private String gender;				//gender    char(3),
  private String region;				//region    varchar2(30),
  private java.sql.Date birth;	//birth     date,
  private List<String> hobby;		//취미
  private String letter;				//letter    char(1),
  private long fid;							//fid       number(10),
  private String status;				//status		char(1),
  private LocalDateTime cdate;	//cdate     timestamp default systimestamp,
  private LocalDateTime udate; 	//udate     timestamp
}
