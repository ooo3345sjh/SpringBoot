package kr.co.vboard.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.vboard.dao.ArticleDAO;
import kr.co.vboard.dao.UserDAO;
import kr.co.vboard.repository.UserRepo;
import kr.co.vboard.utils.PageHandler;
import kr.co.vboard.vo.ArticleVO;
import kr.co.vboard.vo.FileVO;
import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ArticleService {
	
	private ArticleDAO articleDao;
	private FileService fileService;
	
	
	public int rSave(ArticleVO article) {
		
		// 글 등록
		int result = articleDao.insert(article);
		MultipartFile file = article.getFname();
		
		// 파일 업로드
		FileVO fvo = fileService.fileUpload(file);
		
		if(fvo != null) {
			fvo.setParent(article.getNo());
			fileService.addFile(fvo);
		}
		
		return result;
	}
	
	public void getArticles(Map map, int page){
		log.info("ArticleService getArticles...");
		
		PageHandler pageHandler = new PageHandler(articleDao.countAll(), page);  		// 페이징 처리
		List<ArticleVO> articles =  articleDao.selectAll(pageHandler.getLimitStart()); // 게시물 조회
		
		log.debug(articles.toString());
		
		map.put("pageHandler", pageHandler);
		map.put("articles", articles);
	};

}
