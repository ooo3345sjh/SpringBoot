package kr.co.farmstory.vo;

import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.utils.PageHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchConditionTest {
    @Autowired
    private ArticleDAO articleDAO;
    
    @Test
    public void test(){
        SearchCondition sc = new SearchCondition();

        PageHandler ph = new PageHandler(221, sc);

        ph.print();
    }

    @Test
    public void insertTestDate() throws Exception {

//        for(int i=0; i<=110; i++){
            ArticleVO vo = new ArticleVO();
            vo.setTitle("event1");
            vo.setCate("event");
            vo.setRegip("0.0.0.0.1");
            vo.setUid("a101");
            articleDAO.insert(vo);
//        }
    }
}