package kr.co.farmstory.controller;

import kr.co.farmstory.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@PropertySource(value = "classpath:navi.properties", encoding = "UTF-8")
public class NavigationController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Environment environment;

    @GetMapping(value = {"/introduction", "/market", "/croptalk", "/event", "/community"})
    public String navigation(Model m, String cate, Integer page, HttpServletRequest req){

        // 그룹명 가져오기
        int index = req.getRequestURI().lastIndexOf("/") + 1;
        String group = req.getRequestURI().substring(index);

        // 게시물 조회
        articleService.getArticles(m, page);

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, group);

        // 속성 추가
        m.addAttribute("group", group);  // 그룹 이름 영어
        m.addAttribute("cate", cate);    // 현재 카테고리

        return group.equals("introduction") ? group + "/" + cate : "board/list";
    }

    @RequestMapping(value = {"/market", "croptalk", "event", "community"})
    @GetMapping("/write")
    public void write(){

    }


    // properties파일에서 데이터를 가져오는 메서드
    public void addGroupAndCateName(Model m, String group){
        String[] cateList_En = environment.getProperty("group." + group + ".en").split(",");
        String[] cateList_Ko = environment.getProperty("group." + group + ".ko").split(",");
        String group_Ko = environment.getProperty(group);

        m.addAttribute("cateList_En", cateList_En); // 카테고리 영어
        m.addAttribute("cateList_Ko", cateList_Ko); // 카테고리 한글
        m.addAttribute("group_Ko", group_Ko);       // 그룹 이름 한글
    }
}
