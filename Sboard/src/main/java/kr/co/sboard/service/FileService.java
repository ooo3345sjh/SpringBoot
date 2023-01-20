package kr.co.sboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sboard.dao.FileDAO;
import kr.co.sboard.vo.FileVO;

@Service
public class FileService {
	
	@Autowired
	private FileDAO dao;
	
	public FileVO getFile(int no) {
		FileVO vo = dao.select(no); // 파일 조회
		dao.update(vo);				// 파일 다운로드 수 +1
		return vo;
	};
	
	public List<FileVO> getFiles(){
		return dao.selectAll();
	};
	
	public int addFile(FileVO vo) {
		return dao.insert(vo);
	};
	
	public int remove(int no) {
		return dao.delete(no);
	};
	
	public int modify(FileVO vo) {
		return dao.update(vo);
	};
	

}
