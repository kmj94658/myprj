package com.kh.myprj.domain.common.dao;

import java.util.List;

import com.kh.myprj.web.form.Code;

public interface CodeDAO {
	List<Code> getCode(String Pcode);
}