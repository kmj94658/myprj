package com.kh.myprj.domain.board.svc;

import java.util.List;

import com.kh.myprj.domain.board.dto.BoardDTO;
import com.kh.myprj.domain.board.dto.SearchDTO;

public interface BoardSVC {

	/**
	 * 원글 작성
	 * @param boardDTO
	 * @return
	 */
	Long write(BoardDTO boardDTO); //반환타입을 wrapper 클래스로 하는 이유? -자동으로 박싱이 된다. 기본타입은 null값을 못갖는다. 
	
	/**
	 * 답글 작성
	 * @param boardDTO
	 * @return
	 */
	Long reply(BoardDTO boardDTO);
	
	/**
	 * 게시글 수정
	 * @param bnum
	 * @param boardDTO
	 * @return
	 */
	Long modifyItem(Long bnum, BoardDTO boardDTO);
	
	/**
	 * 게시글 목록
	 * @return
	 */
	List<BoardDTO> list();
	List<BoardDTO> list(int startRec, int endRec);
	List<BoardDTO> list(String bcategory, int startRec, int endRec);
	List<BoardDTO> list(SearchDTO searchDTO);
	List<BoardDTO> list(int startRec, int endRec, String searchType, String keyword);
	
	/**
	 * 게시글 상세
	 * @param bnum
	 * @return
	 */
	BoardDTO itemDetail(Long bnum);
	
	/**
	 * 게시글 삭제
	 * @param bnum
	 */
	void delItem(Long bnum);
	
	/**
	 * 게시판 전체 레코드 수
	 * @return
	 */
	long totalRecordCount();
	
	/**
	 * 게시판 카테고리별 전체 레코드 수
	 * @param category
	 * @return
	 */
	long totalRecordCount(String bcategory);
	
	/**
	 * 전체 게시판 검색 레코드 총수
	 * @param searchType
	 * @param keyword
	 * @return
	 */
	long totalRecordCount(String searchType, String keyword);
	
	/**
	 * 게시판 카테고리별 검색 레코드 총수 
	 * @param bcategory
	 * @param searchType
	 * @param keyword
	 * @return
	 */
	long totalRecordCount(String bcategory, String searchType, String keyword);
}
