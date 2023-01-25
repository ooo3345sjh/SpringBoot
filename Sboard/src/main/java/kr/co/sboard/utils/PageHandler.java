package kr.co.sboard.utils;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
public class PageHandler {
    int totalCnt;   // 총 게시물 갯수
    int pageSize;   // 한 페이지의 크기
    int limitStart; // 조회할 페이지 첫 행 
    int naviSize = 10;   // 페이지 내비게이션의 크기
    int totalPage;  // 전체 페이지의 수
    Integer page;       // 현재 페이지
    int beginPage;  // 내비게이션의 첫번째 페이지
    int endPage;    // 내비게이션의 마지막 페이지
    boolean showPrev; // 이전 페이지로 이동하는 링크를 보여줄 것인지의 여부
    boolean showNext; // 다음 페이지로 이동하는 링크를 보여줄 것인지의 여부

    public PageHandler(int totalCnt, int page){
        this(totalCnt, page, 10);
    }

    public PageHandler(int totalCnt, int page, int pageSize){
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;

        totalPage = (int)Math.ceil(totalCnt / (double)pageSize); // Math.ceil(255/10.0) = 26.0  -> (int) 캐스팅 = 26
        beginPage =  (page -1) / naviSize * naviSize + 1; 
        endPage = Math.min(beginPage + naviSize - 1, totalPage);
        showPrev =  beginPage != 1;
        showNext = endPage != totalPage;
        limitStart = (page - 1) * pageSize;
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
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
