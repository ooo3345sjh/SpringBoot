package kr.co.bisan.animal.hostpital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.bisan.animal.hostpital.vo.ItemVO;
import kr.co.bisan.animal.hostpital.vo.ResultVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    @GetMapping(value={"/", "/index"})
    public String index(Model model){

        // API 정보
        String apiURL = "http://apis.data.go.kr/6260000/BusanAnimalHospService/getTblAnimalHospital";
        String serviceKey = "J/7qHWYOh9mzSZ1BTsqG80js8LofeaV8L5mdUeBsd3GCSI7Cydxk8cKyLIkOt6OTYLkJcVHUkaqfghCPVHewWw==";
        String resultType = "json";
        String pageNo = "1";
        String numOfRows = "1000"; // 부산시 전체 동물병원 279

        URI uri = UriComponentsBuilder
                    .fromUriString(apiURL)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", pageNo)
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("resultType", resultType)
                    .encode().build().toUri();

        System.out.println("uri.toString() = " + uri.toString());


        RequestEntity<Void> req = RequestEntity.get(uri).build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        // JSON 문자열
        String jsonData = result.getBody();

        System.out.println("jsonData = " + jsonData);

        // JSON 파싱(Deserialization)
        ObjectMapper om = new ObjectMapper();
        try {
            ResultVO resultVO = om.readValue(jsonData, ResultVO.class);
            List<ItemVO> items = resultVO.getGetTblAnimalHospital().getBody().getItems().getItem();

            System.out.println("items = " + items);

            model.addAttribute("items", items);

        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return "index";
    }
}
