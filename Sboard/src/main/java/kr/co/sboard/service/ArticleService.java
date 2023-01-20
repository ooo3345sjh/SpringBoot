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

import kr.co.sboard.dao.ArticleDAO;
import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.utils.PageHandler;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {

	@Autowired
	private ArticleDAO dao;
	
	@Autowired
	private FileService fileService;
	
	public void getArticles(Model model, int page){
		PageHandler pageHandler = new PageHandler(dao.countAll(), page);  		// 페이징 처리
		List<ArticleVO> articles =  dao.selectAll(pageHandler.getLimitStart()); // 게시물 조회
		
		model.addAttribute("pageHandler", pageHandler);
		model.addAttribute("articles", articles);
	};
	
	public ArticleVO getArticle(int no) {
		return dao.select(no);
	};
	
	@Transactional(rollbackOn = Exception.class)
	public int write(ArticleVO vo)  {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		vo.setUid(((UserEntity)auth.getPrincipal()).getUid());
		
		// 글 등록
		int result = dao.insert(vo);
		MultipartFile file = vo.getFname();
		
		// 파일 업로드
		FileVO fvo = fileUpload(file);
		
		if(fvo != null) {
			fvo.setParent(vo.getNo());
			fileService.addFile(fvo);
		}
		
		return result;
	};
	
	public int delete(int no) {
		return dao.delete(no);
	};
	
	public int modify(ArticleVO vo) {
		return dao.update(vo);
	};
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	public FileVO fileUpload(MultipartFile file) {
		
		if(file.isEmpty()) return null;
		
		// 시스템 경로
		String path = new File(uploadPath).getAbsolutePath();
		
		// 새 파일명 입력
		String oName = file.getOriginalFilename();
		String ext = oName.substring(oName.lastIndexOf("."));
		String nName = UUID.randomUUID().toString() + ext;
		
		
		// 파일 저장
		try {
			file.transferTo(new File(path, nName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		FileVO fvo = FileVO.builder()
					 .oriName(oName)
					 .newName(nName).build();
		return fvo;
	}
	
	public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
		
		Path path = Paths.get(uploadPath + vo.getNewName());
		String contentType = Files.probeContentType(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition
									.builder("attachment")
									.filename(vo.getOriName(), StandardCharsets.UTF_8)
									.build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		
	}
}
