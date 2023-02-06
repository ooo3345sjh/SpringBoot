package kr.co.farmstory.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentVO {

    private Integer cno;
    private Integer pcno;
    private Integer parent;
    private String comment;
    private String uid;
    private String nick;
    private String regip;
    private Date up_rdate;
    private Date rdate;
}
