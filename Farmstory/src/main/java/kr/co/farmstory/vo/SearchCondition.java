package kr.co.farmstory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String group;
    private String cate;
    private Integer no = 0;


    public String getQueryString(Integer page){
        // ?page=1&pageSize=10&option="T"&keyword="title"
        return getQueryString(page, no);
    }

    public String getQueryString(Integer page, Integer no){
        // ?page=1&pageSize=10&option="T"&keyword="title"
        String uri= UriComponentsBuilder.newInstance()
                .queryParam("group", group)
                .queryParam("cate", cate)
                .queryParam("no", no)
                .queryParam("page", page)
//                .queryParam("pageSize", pageSize)
                .toUriString();
        System.out.println("uri = " + uri);
        return UriComponentsBuilder.newInstance()
                .queryParam("group", group)
                .queryParam("cate", cate)
                .queryParam("no", no)
                .queryParam("page", page)
//                .queryParam("pageSize", pageSize)
                .toUriString();
    }

    public String getQueryString(){
        // ?page=1&pageSize=10&option="T"&keyword="title"
        return getQueryString(page);
    }

    public Integer getOffset() {
        return (page-1) * pageSize;
    }

}
