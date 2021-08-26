package com.kh.myprj.web.api;

import java.sql.Date;

import lombok.Data;

@Data
public class FindEmailReq {
	
	private String tel;
	private Date birth;
}
