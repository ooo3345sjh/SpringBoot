package kr.co.vboard.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.vboard.dao.ArticleDAO;
import kr.co.vboard.dao.UserDAO;
import kr.co.vboard.repository.UserRepo;
import kr.co.vboard.vo.ArticleVO;
import kr.co.vboard.vo.FileVO;
import kr.co.vboard.vo.TermsVO;
import kr.co.vboard.vo.UserVO;
import lombok.AllArgsConstructor;

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

}
