package kr.co.farmstory.service;

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

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CommentService {

    private CommentDAO dao;

   public int write(CommentVO vo) throws Exception{

       // 로그인된 객체를 가져온다.
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       UserEntity user = (UserEntity)auth.getPrincipal();
       WebAuthenticationDetails details = (WebAuthenticationDetails)auth.getDetails();

       vo.setUid(user.getUid());
       vo.setNick(user.getNick());
       vo.setRegip(details.getRemoteAddress());

       return dao.insert(vo);
   }

   public int modify(CommentVO vo) throws Exception{
       return 0;
   }
   public int remove(Integer no, String uid) throws Exception{
       return 0;
   }
   public List<CommentVO> getComments() throws Exception{
       return null;
   }
   public CommentVO getComment() throws Exception{
       return null;
   }
   public int getCommentSize(Integer parent) throws Exception{
       return 0;
   }

}
