package kr.co.todo.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleVO {

    int no;
    String content;
    String rdate;
    String status;
    int order;
}
