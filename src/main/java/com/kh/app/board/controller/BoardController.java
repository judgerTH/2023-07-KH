package com.kh.app.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.app.board.dto.BoardListDto;
import com.kh.app.board.dto.BoardSearchDto;
import com.kh.app.board.entity.Favorite;
import com.kh.app.board.entity.PostLike;
import com.kh.app.board.service.BoardService;
import com.kh.app.member.entity.MemberDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * @author o0o_m
 *
 */
@Controller
@Slf4j
@RequestMapping("/board")
@Transactional
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@GetMapping("/freeBoardList.do")
	public String freeBoardList(Model model) {
		List<BoardListDto> freeBoardLists = boardService.freeBoardFindAll();
        log.debug("freeBoardLists = {}", freeBoardLists);
        
        model.addAttribute("freeBoardLists", freeBoardLists);
        
        return "/board/freeBoardList";
		
		
	}
	
	@GetMapping("/marketBoardList.do")
	public void marketBoardList() {
		
	}
	
	@GetMapping("/todayFoodBoardList.do")
	public void todayFoodBoardList() {
		
	}
	
	@GetMapping("/sharingInformationBoardList.do")
	public void sharingInformationBoardList() {
		
	}
	
	@GetMapping("/askCodeBoardList.do")
	public void askCodeBoardList() {
		
	}
	
	@GetMapping("/studyBoardList.do")
	public void studyBoardList() {
		
	}
	
	@GetMapping("/preStudentBoardList.do")
	public String preStudentBoardList(Model model) {
		List<BoardListDto> preStudentBoardList = boardService.preStudentBoardFindAll();
        log.debug("preStudentBoardList = {}", preStudentBoardList);
        
        model.addAttribute("preStudentBoardList", preStudentBoardList);
        
        return "/board/preStudentBoardList";
	}
	
	@GetMapping("/graduateBoardList.do")
	public String graduateBoardList(Model model) {
		List<BoardListDto> graduateBoardList = boardService.graduateBoardFindAll();
        log.debug("graduateBoardList = {}", graduateBoardList);
        
        model.addAttribute("graduateBoardList", graduateBoardList);
        
        return "/board/graduateBoardList";
	}
	
	@GetMapping("/employeeBoardList.do")
	public String employeeBoardList(Model model) {
		List<BoardListDto> employeeBoardList = boardService.employeeBoardFindAll();
        log.debug("employeeBoardList = {}", employeeBoardList);
        
        model.addAttribute("employeeBoardList", employeeBoardList);
        
        return "/board/employeeBoardList";
	}
	
	
	
	/**
	 * index.jsp에서 제목 + 게시글 검색
	 * @param keyword
	 * @param model
	 * @return
	 */
	@GetMapping("/boardSearch.do")
    public String boardSearch(@RequestParam String keyword, Model model) {
        List<BoardSearchDto> boards = boardService.findAllByKeyword(keyword);
        log.debug("boards = {}", boards);
        log.debug("keyword = {}", keyword);
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        return "/board/boardListByKeyword";
    }
	
	/**
	 * 내 즐겨찾기
	 * @param principal
	 * @return
	 */
	@GetMapping("/myBoards.do")
	public ResponseEntity<?> myBoards(@AuthenticationPrincipal MemberDetails principal) {
//		String memberId = principal.getMemberId();
		List<BoardSearchDto> boards = boardService.findAllByMemberId("gmlwls");
		log.debug("boards = {}", boards);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(Map.of("boards", boards));
	}
	
	/**
	 * 즐겨찾기 했는지 안했는지
	 * @param principal
	 * @param _boardId
	 * @return
	 */
	@GetMapping("/favorite.do")
	public ResponseEntity<?> isFavorite(@AuthenticationPrincipal MemberDetails principal, @RequestParam String _boardId) {
//		String memberId = principal.getMemberId();
		int boardId = Integer.parseInt(_boardId);
		
		Favorite favorite = boardService.findFavoriteByMemberId(boardId, "gmlwls");
		log.debug("favorite = {}", favorite);
		
		boolean available = true;
		if(favorite == null)
			available = false;
		log.debug("availalbe = {}", available);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(Map.of("available", available));
	}
	
	/**
	 * 즐겨찾기 기능
	 * @param principal
	 * @param _boardId
	 * @return
	 */
	@PostMapping("/favorite.do")
	public ResponseEntity<?> favorite(@AuthenticationPrincipal MemberDetails principal, @RequestParam String _boardId) {
//		String memberId = principal.getMemberId();
		int boardId = Integer.parseInt(_boardId);
		
		Favorite favorite = boardService.findFavoriteByMemberId(boardId, "gmlwls");
		log.debug("favorite = {}", favorite);
		
		boolean available = true;
		if(favorite == null)
			available = false;
		log.debug("availalbe = {}", available);
		
		int result = 0;
		if(available) {
			result = boardService.deleteFavoriteByMemberId(boardId, "gmlwls");
		}
		else {
			result = boardService.insertFavoriteByMemberId(boardId, "gmlwls");
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(Map.of("available", available));
		}
	
	/**
	 * 게시글조회
	 * @param id
	 * @param model
	 */
	@GetMapping("/boardDetail.do")
	public void boardDetail(@RequestParam int id, Model model) {
		BoardListDto postDetail = boardService.findById(id);
		log.debug("postDetail = {}", postDetail);
		model.addAttribute("postDetail", postDetail);
	}
	
	/**
	 * 해당 게시물에 공감(좋아요) 했는지 안했는지
	 * @param principal
	 * @param _postId
	 * @return
	 */
	@GetMapping("/postLike.do")
	public ResponseEntity<?> isPostLike(@AuthenticationPrincipal MemberDetails principal, @RequestParam String _postId) {
//		String memberId = principal.getMemberId();
		int postId = Integer.parseInt(_postId);
		
		PostLike postLike = boardService.findPostLikeByMemberId(postId, "alfn");
		log.debug("postLike = {}", postLike);
		
		boolean available = true;
		if(postLike == null)
			available = false;
		log.debug("availalbe = {}", available);
		
		PostLike likeCount = boardService.findPostLikeCount(postId);
		log.debug("likeCount = {}", likeCount);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(Map.of("available", available, "likeCount", likeCount));
	}
	
	/**
	 * 해당 게시물에 좋아요(공감) 기능
	 * @param principal
	 * @param _postId
	 * @return
	 */
	@PostMapping("/postLike.do")
	public ResponseEntity<?> postLike(@AuthenticationPrincipal MemberDetails principal, @RequestParam String _postId) {
//		String memberId = principal.getMemberId();
		int postId = Integer.parseInt(_postId);
		
		PostLike postLike = boardService.findPostLikeByMemberId(postId, "alfn");
		log.debug("postLike = {}", postLike);
		
		boolean available = true;
		if(postLike == null)
			available = false;
		log.debug("availalbe = {}", available);
		
		int result = 0;
		if(available) {
			result = boardService.deletePostLikeByMemberId(postId, "alfn");
		}
		else {
			result = boardService.insertPostLikeByMemberId(postId, "alfn");
		}
		
		PostLike likeCount = boardService.findPostLikeCount(postId);
		log.debug("postLikeCount = {}", likeCount);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(Map.of("available", available, "likeCount", likeCount));
	}

}
