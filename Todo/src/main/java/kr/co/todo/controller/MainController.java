package kr.co.todo.controller;

import kr.co.todo.entity.ArticleEntity;
import kr.co.todo.service.ArticleService;
import kr.co.todo.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
public class MainController {

    @Autowired
    private ArticleService service;

    @GetMapping(value = {"/", "/index"})
    public String index(Model m){
        m.addAttribute("map", service.select());
        return "/index";
    }

    @ResponseBody
    @PostMapping("/")
    public Map write(@RequestBody ArticleVO vo){
        log.info("MainController Post write...");
        log.info(vo.toString());

        ArticleEntity entity = ArticleEntity.builder()
                .content(vo.getContent())
                .rdate(new Date())
                .status("ready")
                .build();

        entity = service.insert(entity);

        Map map = new HashMap();
        map.put("entity", entity);
        return map;
    }

    @ResponseBody
    @DeleteMapping("/{no}")
    public Map delete(@PathVariable int no){
        int result = service.delete(no);
        Map map = new HashMap();
        map.put("result", result);
        return map;
    }

    @ResponseBody
    @PatchMapping("/{no}")
    public Map update(@RequestBody Map<String, Integer> map){
        int result = service.update(map.get("no"), map.get("status"));
        map.put("result", result);
        return map;
    }
}
