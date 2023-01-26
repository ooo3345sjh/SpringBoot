package kr.co.farmstory.service;

import java.util.List;

import javax.transaction.Transactional;

import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.utils.PageHandler;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.FileVO;
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

	public void getArticles(Model model, Integer page){
		log.info("ArticleService getArticles...");

		if(page == null) page = 1;
		PageHandler pageHandler = new PageHandler(dao.countAll(), page);  		// 페이징 처리
		List<ArticleVO> articles =  dao.selectAll(pageHandler.getLimitStart()); // 게시물 조회
		
		log.info(articles.toString());
		
		model.addAttribute("pageHandler", pageHandler);
		model.addAttribute("articles", articles);
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

//		vo.setUid(auth.getName());		// uid 저장
		vo.setUid("a101");		// uid 저장
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
	
	public int delete(int no) {
		log.info("ArticleService delete...");
		return dao.delete(no);
	};
	
	public int modify(ArticleVO vo) {
		log.info("ArticleService modify...");
		return dao.update(vo);
	};
}
