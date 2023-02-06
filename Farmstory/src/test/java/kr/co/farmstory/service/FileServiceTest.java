//package kr.co.farmstory.service;
//
//import groovy.util.logging.Slf4j;
//import kr.co.farmstory.dao.ArticleDAO;
//import kr.co.farmstory.vo.ArticleVO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.File;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@SpringBootTest
//class FileServiceTest {
//
//    @Autowired
//    private ArticleDAO dao;
//
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadPath;
//
//    @Test
//    public void fileDelete(){
//        ArticleVO vo = dao.select(843);
//        File file = new File(uploadPath + vo.getFileVO().getNewName());
//        System.out.println("file.exists() = " + file.exists());
//        assertEquals(file.exists(), true);
//    }
//
//}