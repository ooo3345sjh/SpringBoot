package kr.co.farmstory.service;

import kr.co.farmstory.dao.ArticleDAO;
import kr.co.farmstory.dao.CommentDAO;
import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.vo.CommentVO;
import kr.co.farmstory.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CommentService {

    private CommentDAO commentDAO;
    private ArticleDAO articleDAO;

    @Transactional(rollbackFor = Exception.class)
    public int write(CommentVO vo) throws Exception{

       // 로그인된 객체를 가져온다.
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();

       // 비로그인 유저인지 확인
       Boolean isAnonymous = auth.getAuthorities().toString().contains("ROLE_ANONYMOUS");

       // null 또는 비로그인 유저일시 에러 발생
       if(auth == null || isAnonymous) throw  new Exception("Not Exist Authentication");

       UserEntity user = (UserEntity)auth.getPrincipal();
       WebAuthenticationDetails details = (WebAuthenticationDetails)auth.getDetails();

       vo.setUid(user.getUid());
       vo.setNick(user.getNick());
       vo.setRegip(details.getRemoteAddress());

       // 댓글 추가
       Integer result = commentDAO.insert(vo);
        System.out.println("result = " + result);
       
       // 댓글 갯수 +1 
       result = articleDAO.updateCommentCnt(1, vo.getParent());
       
       return result;
   }

   public int modify(CommentVO vo) throws Exception{
       return commentDAO.update(vo);
   }

   @Transactional(rollbackFor = Exception.class)
   public int remove(CommentVO vo, String uid) throws Exception{

       // 댓글 삭제
       Integer result = commentDAO.delete(vo);

       // 댓글 갯수 -1
       result = articleDAO.updateCommentCnt(-1, vo.getParent());

       return result;
   }

   public List<CommentVO> getComments(Integer parent) throws Exception{
       return commentDAO.selectAll(parent);
   }
   public CommentVO getComment() throws Exception{
       return null;
   }
   public int getCommentSize(Integer parent) throws Exception{
       return 0;
   }

}
