package kr.co.bisan.animal.hostpital.vo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ItemVO {
    private String gugun;
    private String animal_hospital;
    private String approval;
    private String road_address;
    private String tel;
    private String lat;
    private String lon;
    private String basic_date;
}
