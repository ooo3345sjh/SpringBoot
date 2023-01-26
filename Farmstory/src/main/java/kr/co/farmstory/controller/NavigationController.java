package kr.co.farmstory.controller;

import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.vo.ArticleVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
@PropertySource(value = "classpath:navi.properties", encoding = "UTF-8")
@AllArgsConstructor
@RequestMapping("/gnb")
public class NavigationController {

    private ArticleService articleService;
    private Environment environment;

    @GetMapping("")
    public String navigation(Model m, String group, String cate, Integer page){
        log.info("NavigationCotroller navigation...");

        // 게시물 조회
        if(!group.equals("introduction")) articleService.getArticles(m, page);

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, group);

        return group.equals("introduction") ? group + "/" + cate : "board/list";
    }


    @GetMapping("/view")
    public String view(Model m, String group, Integer no){
        log.info("NavigationCotroller view...");

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, group);
        
        // 번호에 해당하는 게시글 조회
        m.addAttribute("article", articleService.getArticle(no));

        return "board/view" ;
    }

    @GetMapping("/write")
    public String write(Model m, String group){
        log.info("GET wirte...");

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, group);

        return "board/write" ;
    }
    @PostMapping("/write")
    public String write(ArticleVO vo, String group, String cate){
        log.info("POST wirte...");

        int result = articleService.write(vo);
        return "redirect:/gnb" + createQueryString(group, cate, 1, null);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> map){

        log.info(map.toString());
        int result = articleService.delete(Integer.parseInt((String)map.get("no")));
        map.put("result", result);

        return map;
    }

    @GetMapping("/modify")
    public String modify(Model m, String group, Integer no){
        log.info("GET modify...");

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, group);

        m.addAttribute(articleService.getArticle(no));

        return "board/modify" ;
    }
    @PostMapping("/modify")
    public String modify(ArticleVO vo, String group, String cate, Integer page){
        log.info("POST modify...");
        articleService.modify(vo);
        return "redirect:/gnb/view" + createQueryString(group, cate, page, vo.getNo());
    }


    // properties파일에서 가져온 데이터를 Model에 추가하는 메서드
    public void addGroupAndCateName(Model m, String group){
        String[] cateList_En = environment.getProperty("group." + group + ".en").split(",");
        String[] cateList_Ko = environment.getProperty("group." + group + ".ko").split(",");
        String group_Ko = environment.getProperty(group);

        m.addAttribute("cateList_En", cateList_En); // 카테고리 영어
        m.addAttribute("cateList_Ko", cateList_Ko); // 카테고리 한글
        m.addAttribute("group_Ko", group_Ko);       // 그룹 이름 한글
    }

    // 쿼리스트링 만드는 메서드
    public String createQueryString(String group, String cate, Integer page, Integer no){
        return UriComponentsBuilder.newInstance()
                .queryParam("group", group)
                .queryParam("cate", cate)
                .queryParam("page", page)
                .queryParam("no", no)
                .build().toString();
    }
}
