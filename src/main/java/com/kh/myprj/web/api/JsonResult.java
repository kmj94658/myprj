package com.kh.myprj.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 							//getter,setter,toString,equals,hashCode
@AllArgsConstructor //모든 멤버를 매개변수로 하는 생성자 생성하면 디폴트 생성자는 안만들어 진다
@NoArgsConstructor 	//디폴트 생성자 만들기
public class JsonResult<T> {

	private String rtcd;
	private String rtmsg;
	private T data;
}
