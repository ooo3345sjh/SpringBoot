package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.service.ArticleService;
import kr.co.farmstory.service.FileService;
import kr.co.farmstory.vo.ArticleVO;
import kr.co.farmstory.vo.SearchCondition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@PropertySource(value = "classpath:navi.properties", encoding = "UTF-8")
@AllArgsConstructor
@RequestMapping("/gnb")
public class NavigationController {

    private ArticleService articleService;
    private FileService fileService;
    private Environment environment;

    @GetMapping("")
    public String navigation(Model m, SearchCondition sc){
        log.info("NavigationCotroller navigation...");

        // 게시물 조회
        if(!sc.getGroup().equals("introduction")) {
            articleService.getArticles(m, sc);

            // 오늘 날짜 가장 빠른 정시의 밀리세컨드
            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        }

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, sc.getGroup());

        return sc.getGroup().equals("introduction") ? sc.getGroup() + "/" + sc.getCate() : "board/list";
    }

    @GetMapping("/view")
    public String view(Model m, SearchCondition sc, @AuthenticationPrincipal UserEntity user){
        log.info("NavigationCotroller view...");
        log.info(sc.toString());
        
        // 번호에 해당하는 게시글 조회
        ArticleVO vo = articleService.getArticle(sc.getNo());

        // 모델 추가
        m.addAttribute("article", vo);
        m.addAttribute("sc", sc);
        if(user != null) m.addAttribute("nick", user.getNick());

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, sc.getGroup());

        return "board/view" ;
    }

    @GetMapping("/write")
    public String write(Model m, SearchCondition sc){
        log.info("GET wirte...");

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, sc.getGroup());

        // 검색 조건 객체 추가
        m.addAttribute("sc", sc);

        return "board/write" ;
    }

    @PostMapping("/write")
    public String write(ArticleVO vo, SearchCondition sc){
        log.info("POST wirte...");

        // 게시글 작성
        int result = articleService.write(vo);
        
        return "redirect:/gnb" + sc.getQueryString();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(@RequestBody SearchCondition sc) {
        log.info("POST delete...");

        Integer result = 0;

        try {

            // 게시글 삭제
            result = articleService.delete(sc.getNo());

        } catch (Exception e){
            e.printStackTrace();
        }

        // 결과 저장
        Map<String, String> map = new HashMap<>();
        map.put("result", result.toString());

        return map;
    }

    @GetMapping("/modify")
    public String modify(Model m, SearchCondition sc){
        log.info("GET modify...");

        // 프로퍼티 파일 조회
        addGroupAndCateName(m, sc.getGroup());
        
        // 검색 조건 객체 추가
        m.addAttribute("sc", sc);

        // 게시글 가져와서 모델에 추가
        m.addAttribute(articleService.getArticle(sc.getNo()));

        return "board/modify" ;
    }

    @PostMapping("/modify")
    public String modify(ArticleVO vo, SearchCondition sc){
        log.info("POST modify...");

        // 게시글 수정
        articleService.modify(vo);

        return "redirect:/gnb/view" + sc.getQueryString();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(int fno) throws IOException {
        log.info("POST download...");

        return fileService.fileDownload(fno);
    }

    @PostMapping("/hit/{no}")
    @ResponseBody
    public void updateHit(@PathVariable Integer no){

        log.info("Navigation updateHit...");

        try {
            articleService.updateHit(no);
        } catch (Exception e){
            e.printStackTrace();
        }

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

}
