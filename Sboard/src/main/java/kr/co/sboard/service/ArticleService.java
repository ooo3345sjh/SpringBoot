package kr.co.sboard.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import kr.co.sboard.dao.ArticleDAO;
import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.utils.PageHandler;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import kr.co.sboard.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

	private ArticleDAO dao;
	private FileService fileService;
	
	public ArticleService(ArticleDAO dao, FileService fileService) {
		this.dao  = dao;
		this.fileService = fileService;
	}
	
	public void getArticles(Model model, int page){
		log.info("ArticleService getArticles...");
		
		PageHandler pageHandler = new PageHandler(dao.countAll(), page);  		// 페이징 처리
		List<ArticleVO> articles =  dao.selectAll(pageHandler.getLimitStart()); // 게시물 조회
		
		log.debug(articles.toString());
		
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
	
	public int delete(int no) {
		log.info("ArticleService delete...");
		return dao.delete(no);
	};
	
	public int modify(ArticleVO vo) {
		log.info("ArticleService modify...");
		return dao.update(vo);
	};
}
