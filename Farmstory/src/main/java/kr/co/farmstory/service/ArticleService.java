package kr.co.farmstory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.utils.PageHandler;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
import kr.co.farmstory.vo.SearchCondition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

	private ArticleDAO dao;
	private FileService fileService;

	public void getArticles(Model m, SearchCondition sc){
		log.info("ArticleService getArticles...");

		int totalCnt = dao.countAll(sc);

		/** 검색 페이지 > 전체 페이수일 경우 실행 **/
		// 전체 페이지수
		int totalPage = (int)Math.ceil(totalCnt/(double)sc.getPageSize());

		// 전체 페이지수가 현재 페이지수 보다 크면 전체 페이지수로 값 저장
		if(sc.getPage() > totalPage) sc.setPage(totalPage);
		/**                                 **/

		PageHandler pageHandler = new PageHandler(totalCnt, sc);  // 페이징 처리
		List<ArticleVO> articles =  dao.selectAll(sc); 			  // 게시물 조회
		
		log.info(articles.toString());

		m.addAttribute("ph", pageHandler);
		m.addAttribute("articles", articles);
	};
	
	public ArticleVO getArticle(int no) {
		log.info("ArticleService getArticle...");
		return dao.select(no);
	};
	
	@Transactional(rollbackOn = Exception.class)
	public int write(ArticleVO vo) {
		log.info("ArticleService write...");
		
		// 로그인된 객체를 가져온다.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WebAuthenticationDetails details = (WebAuthenticationDetails)auth.getDetails();

		vo.setUid(auth.getName());		// uid 저장
		vo.setRegip(details.getRemoteAddress());	// regip 저장
		
		// 글 등록
		int result = dao.insert(vo);
		MultipartFile file = vo.getFname();
		
		// 파일 업로드
		FileVO fvo = fileService.fileUpload(file);
		
		if(fvo != null) {
			fvo.setParent(vo.getNo());
			fileService.addFile(fvo);
		}
		log.info(String.valueOf(result));
		return result;
	};

	@Transactional(rollbackOn = Exception.class)
	public int delete(int no) throws Exception {
		log.info("ArticleService delete...");

		ArticleVO article = dao.select(no);
		Integer result = dao.delete(no);

		if(article.getFile() > 0)
			fileService.fileRemove(article.getFileVO());

		return result;
	};
	
	public int modify(ArticleVO vo) {
		log.info("ArticleService modify...");
		return dao.update(vo);
	};

	public int countAll(SearchCondition sc) {
		return dao.countAll(sc);
	}

	public int updateCommentCnt(Integer count, Integer no) throws Exception {
		return dao.updateCommentCnt(count, no);
	}

	public int updateHit(Integer no) throws Exception {
		return dao.updateHit(no);
	}

	public Map<String, List<ArticleVO>> selectCate() throws Exception {
		List<ArticleVO> cateList = dao.selectCate();
		return  cateList.stream().collect(Collectors.groupingBy(ArticleVO::getCate));
	};
}
