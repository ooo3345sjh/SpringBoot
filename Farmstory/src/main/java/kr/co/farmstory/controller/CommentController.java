package kr.co.farmstory.controller;

import kr.co.farmstory.service.CommentService;
import kr.co.farmstory.vo.CommentVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService service;

    @GetMapping("/comment")
    public String view(){
        return null;
    }

    @PostMapping("/comment")
    public Map write(@RequestBody CommentVO vo){
        int result = 0;

        try {
            result = service.write(vo);
        } catch (Exception e){
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

}
