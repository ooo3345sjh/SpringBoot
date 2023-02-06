package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.service.CommentService;
import kr.co.farmstory.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService service;

    @GetMapping("/comment/{no}") // /comment?bno=12
    public Map view(@PathVariable(name = "no") Integer parent){
        System.out.println("parent = " + parent);

        // 반환 객체
        Map map = new HashMap<>();

        // 댓글 리스트 객체 선언
        List<CommentVO> comments = null;

        // 오늘 날짜 가장 빠른 정시의 밀리세컨드
        Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        map.put("startOfToday", startOfToday.toEpochMilli());

        try {
            comments = service.getComments(parent);
        } catch (Exception e){
            e.printStackTrace();
        }

        log.info(comments.toString());
        // 댓글 리스트 저장
        map.put("comments", comments);

        return map;
    }

    @PostMapping("/comment")
    public Map write(@RequestBody CommentVO vo){
        int result = 0;

        // 댓글 줄바꿈 "<br>" 변경 저장
        vo.setComment(vo.getComment().replaceAll("\\n", "<br>"));

        try {
            result = service.write(vo);
        } catch (Exception e){
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    @DeleteMapping("/comment/{cno}")
    public Map delete(@PathVariable Integer cno, @AuthenticationPrincipal UserEntity user, @RequestBody CommentVO vo){
        log.info("Delete comment...");
        log.info(vo.toString());

        Integer result = 0;

        try {
            
            // 로그인한 사용자 정보가 없을 경우 에러 발생
            if(user == null) throw new NullPointerException("Not Exist Authentication Information");

            result = service.remove(vo, user.getUid());

        } catch (Exception e){
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

    @PatchMapping("/comment/{cno}")
    public Map modify(@PathVariable Integer cno, @AuthenticationPrincipal UserEntity user, @RequestBody CommentVO vo){
        log.info("modify comment...");
        log.info(vo.toString());

        Integer result = 0;

        try {

            // 로그인한 사용자 정보가 없을 경우 에러 발생
            if(user == null) throw new NullPointerException("Not Exist Authentication Information");

            result = service.modify(vo);

        } catch (Exception e){
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("result", result);

        return map;
    }

}
