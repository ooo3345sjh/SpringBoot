package kr.co.farmstory.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import kr.co.farmstory.dao.FileDAO;
import kr.co.farmstory.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	@Autowired
	private FileDAO dao;
	
	public FileVO getFile(int no) {
		FileVO vo = dao.select(no); // 파일 조회
		dao.update(vo);				// 파일 다운로드 수 +1
		return vo;
	}
	
	public List<FileVO> getFiles(){
		return dao.selectAll();
	}
	
	public int addFile(FileVO vo) {
		return dao.insert(vo);
	}
	

	public int modify(FileVO vo) {return dao.update(vo);}

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	public void fileRemove(FileVO vo) {
		log.info("fileService fileRemove...");

		// 시스템 경로
		File file = new File(uploadPath + vo.getNewName());

		if(file.exists())
			file.delete();
	}

	public FileVO fileUpload(MultipartFile file) {
		log.info("fileService fileUpload...");
		
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
	
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Resource> fileDownload(int fno) throws IOException {
		log.info("ArticleService fileDownload...");
		
		FileVO vo = getFile(fno);
		
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
