package kr.co.sboard.utils;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
public class PageHandler {
    int totalCnt;   // 총 게시물 갯수
    int pageSzie;   // 한 페이지의 크기
    int naviSzie = 10;   // 페이지 내비게이션의 크기
    int totalPage;  // 전체 페이지의 수
    Integer page;       // 현재 페이지
    int beginPage;  // 내비게이션의 첫번째 페이지
    int endPage;    // 내비게이션의 마지막 페이지
    boolean showPrev; // 이전 페이지로 이동하는 링크를 보여줄 것인지의 여부
    boolean showNext; // 다음 페이지로 이동하는 링크를 보여줄 것인지의 여부

    public PageHandler(int totalCnt, int page){
        this(totalCnt, page, 10);
    }

    public PageHandler(int totalCnt, int page, int pageSzie){
        this.totalPage = totalCnt;
        this.page = page;
        this.pageSzie = pageSzie;

        totalPage = (int)Math.ceil(totalCnt / (double)pageSzie); // Math.ceil(255/10.0) = 26.0  -> (int) 캐스팅 = 26
        beginPage =  (page / naviSzie) * naviSzie + 1;
        endPage = Math.min(beginPage + naviSzie - 1, totalPage);
        showPrev =  beginPage != 1;
        showNext = endPage != totalPage;
    }

    void print(){
        System.out.println("page = " + page);
        System.out.println(showPrev ? "[PREV] " : "");
        for(int i = beginPage; i <= endPage; i++){
            System.out.println(i + " ");
        }
        System.out.println(showNext ? "[NEXT]" : "");

    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSzie=" + pageSzie +
                ", naviSzie=" + naviSzie +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
