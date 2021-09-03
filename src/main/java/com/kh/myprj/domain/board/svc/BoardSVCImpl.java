package com.kh.myprj.domain.board.svc;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.myprj.domain.board.dao.BoardDAO;
import com.kh.myprj.domain.board.dto.BoardDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardSVCImpl implements BoardSVC {

	private final BoardDAO boardDAO;
	
	//원글 작성
	@Override
	public Long write(BoardDTO boardDTO) {
		Long bnum = boardDAO.write(boardDTO);
		return bnum;
	}

	//답글 작성
	@Override
	public Long reply(BoardDTO boardDTO) {
		Long bnum = boardDAO.reply(boardDTO);
		return bnum;
	}

	//게시글 수정
	@Override
	public Long modifyItem(Long bnum, BoardDTO boardDTO) {
		Long modifiedbnum = boardDAO.modifyItem(bnum, boardDTO);
		return modifiedbnum;
	}

	//게시글 목록
	@Override
	public List<BoardDTO> list() {
		List<BoardDTO> list = boardDAO.list();
		return list;
	}

	//게시글 상세
	@Override
	public BoardDTO itemDetail(Long bnum) {
		BoardDTO boardDTO = boardDAO.itemDetail(bnum);
		return boardDTO;
	}

	//게시글 삭제
	@Override
	public void delItem(Long bnum) {
		boardDAO.delItem(bnum);

	}

}
