//package kr.co.farmstory.service;
//
//
//import groovy.util.logging.Slf4j;
//import kr.co.farmstory.dao.ArticleDAO;
//import kr.co.farmstory.vo.ArticleVO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@Slf4j
//@SpringBootTest
//class ArticleServiceTest {
//
//    @Autowired
//    private ArticleService service;
//
//    @Autowired
//    private ArticleDAO dao;
//
//    @Test
//    @DisplayName("1. 카테고리별 collection grouping ")
//    public void cateMappingTest() throws Exception {
////        System.out.println("service = " + service.selectCate());
//        Map<String, List<ArticleVO>> map =service.selectCate();
//
////        System.out.println("map = " + map);
//        System.out.println("map grow = " + map.get("grow"));
//        System.out.println("map story = " + map.get("story"));
//        System.out.println("map school = " + map.get("school"));
//        System.out.println("map notice = " + map.get("notice"));
//        System.out.println("map feq = " + map.get("feq"));
//        System.out.println("map qna = " + map.get("qna"));
//
//    }
//
//    @Test
//    @DisplayName("2. 카테고리별 조회 성능 비교 ")
//    public void cateSeletePerfomanceTest() throws Exception {
//        Date start = new Date();
//        System.out.println("dao.selectCate() = " + dao.selectCate());
//        Date end = new Date();
//
//        System.out.println("start-end = " + (end.getTime() - start.getTime()));
//
//        start = new Date();
//        System.out.println("dao.selectCate() = " + dao.selectCate2());
//        end = new Date();
//
//        System.out.println("start-end = " + (end.getTime() - start.getTime()));
//    }
//}