package kr.co.sboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.sboard.vo.FileVO;

@Mapper
@Repository
public interface FileDAO {
	public FileVO select(int no);
	public List<FileVO> selectAll();
	public int insert(FileVO vo);
	public int delete(int no);
	public int update(FileVO vo);
}
