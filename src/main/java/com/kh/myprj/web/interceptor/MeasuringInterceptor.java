package com.kh.myprj.web.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MeasuringInterceptor implements HandlerInterceptor {

	public static final String LOG_ID = "logid";
	
	/*
	 * boolean타입.
	 * 컨트롤러 호출전에 실행.
	 * 반환이 true - 다음단계로 진행(다음 인터셉터 혹은 컨트롤러 실행).
	 * 반환이 false - 흐름을 끊어서 요청 url을 다른곳으로 다이렉트시켜 다른 흐름 만들어 낸다. 컨트롤러 실행 중지.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("MeasuringInterceptor.preHandle");
		
		String requestURI = request.getRequestURI();
		String uuid = UUID.randomUUID().toString();
		
		//setter로 정보 저장 후 밑에서 사용 가능
		request.setAttribute(LOG_ID, uuid);
		request.setAttribute("beginTime", System.currentTimeMillis());
		
		if(handler instanceof HandlerMethod) {
			//호출할 컨트롤러 메서드의 모든 정보를 갖고 있다
			HandlerMethod hm = (HandlerMethod) handler; 
		}
		
		//HandlerInterceptor.super.preHandle(request, response, handler);
		log.info("Request [{}][{}][{}]",uuid,requestURI, handler);
		return true;
	}
	
	/*
	 * 컨트롤러 실행 후 호출됨.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("MeasuringInterceptor.postHandle");
		
		String uuid = (String) request.getAttribute(LOG_ID);
		log.info("Request [{}][{}][{}]",uuid,modelAndView);
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	/*
	 * 뷰가 렌더링 되고 클라이언트 응답후 호출된다.
	 * 컨트롤러에서 예외 발생하면 그 exception에 대한 정보 알 수 있다.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("MeasuringInterceptor.afterCompletion");
		
		String requestURI = request.getRequestURI();
		String uuid = (String) request.getAttribute(LOG_ID);
		
		long beginTime = (long) request.getAttribute("beginTime");
		long endTime = System.currentTimeMillis();
		
		log.info("Request [{}][{}][실행시간:{}][{}]", uuid, requestURI, (endTime-beginTime), handler);
		
		if(ex != null) {
			log.error("afterCompletion error!", ex);
		}
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
