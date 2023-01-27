package kr.co.farmstory.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentVO {

    private Integer no;
    private Integer parent;
    private String comment;
    private String uid;
    private String nick;
    private String regip;
    private String up_rdate;
    private String rdate;
}
