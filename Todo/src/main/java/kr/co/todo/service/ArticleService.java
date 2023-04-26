package kr.co.todo.service;

import kr.co.todo.dao.ArticleDAO;
import kr.co.todo.entity.ArticleEntity;
import kr.co.todo.repository.ArticleRepo;
import kr.co.todo.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepo repo;

    @Autowired
    private ArticleDAO dao;

    public List<ArticleVO> select(){
        return dao.select();
    };

    public ArticleEntity insert(ArticleEntity entity){
        return repo.save(entity);
    };

    public int delete(int no){
        return dao.delete(no);
    };
    public int deleteAll(){
        return dao.deleteAll();
    };
    public int update(int no, int status){
        return dao.update(no, convertStatus(status));
    };

    public String convertStatus(int status){
        String statusStr = null;
        switch (status){
            case 1: statusStr = "ready"; break;
            case 2: statusStr = "doing"; break;
            case 3: statusStr = "done"; break;
        }
        return statusStr;
    }
}
