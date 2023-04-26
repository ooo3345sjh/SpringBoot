package kr.co.vboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.vboard.vo.FileVO;


@Mapper
@Repository
public interface FileDAO {
	public FileVO select(int fno);
	public List<FileVO> selectAll();
	public int insert(FileVO vo);
	public int delete(int no);
	public int update(FileVO vo);
}
