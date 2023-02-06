package kr.co.bisan.animal.hostpital.vo;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ResultVO {

    private GetTblAnimalHospotal getTblAnimalHospital;

    @Setter
    @Getter
    public class GetTblAnimalHospotal {
        private Header header;
        private Body body;

        @Setter
        @Getter
        public class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Setter
        @Getter
        public class Body {
            private Items items;
            private int numOfRows;
            private int pageNo;
            private int totalCount;

            @Setter
            @Getter
            public class Items {
                private List<ItemVO> item;
            }
        }
    }
}
