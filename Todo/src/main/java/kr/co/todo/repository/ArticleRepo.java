package kr.co.todo.repository;

import kr.co.todo.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo  extends JpaRepository<ArticleEntity, Integer> {

}
