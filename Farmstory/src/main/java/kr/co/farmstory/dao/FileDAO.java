package kr.co.farmstory.dao;

import java.util.List;

import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface FileDAO {
	public FileVO select(int fno);
	public List<FileVO> selectAll();
	public int insert(FileVO vo);
	public int delete(int no);
	public int update(FileVO vo);
}
