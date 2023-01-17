package kr.co.ch09.controller;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ch09.vo.BookVO;
import kr.co.ch09.vo.NaverResultVO;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@GetMapping("/list")
	public void list(String text, Model model) {
		
		// 네이버 검색 API 요청
		String clientId = "_dYUnkWfa4WkR5RqU2id";
		String clientSecret = "MHb6ZXHLhW";
		
		URI uri = UriComponentsBuilder
					.fromUriString("https://openapi.naver.com")
					.path("/v1/search/book.json")
					.queryParam("query", text)
					.queryParam("display", 10)
					.queryParam("start", 1)
					.queryParam("sort", "sim")
					.encode()
					.build()
					.toUri();
		
		RequestEntity<Void> req = RequestEntity
									.get(uri)
									.header("X-Naver-Client-Id", clientId)
									.header("X-Naver-Client-Secret", clientSecret)
									.build();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
		//ResponseEntity<String> resp = restTemplate.exchange(req,  new ParameterizedTypeReference<List<NaverResultVO>>() {});
		
		// JSON 파싱
		ObjectMapper om = new ObjectMapper();
		NaverResultVO resultVo = null;
		
		try {
			resultVo = om.readValue(resp.getBody(), NaverResultVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		List<BookVO> books = resultVo.getItems();
		model.addAttribute("books", books);
	}

}
