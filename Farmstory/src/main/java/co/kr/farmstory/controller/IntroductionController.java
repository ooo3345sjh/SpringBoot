package co.kr.farmstory.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/introduction")
@PropertySource("classpath:navi.properties")
public class IntroductionController {

    @Autowired
    Environment environment;

    @GetMapping("/hello")
    public String hello(Model m){
        m.addAttribute("group", true);
        return "introduction/hello";
    }
    @GetMapping("/direction")
    public String direction(Model m){
        m.addAttribute("group", true);
        List<String> groupEn = Arrays.stream(environment.getProperty("group.community.en").split(",")).collect(Collectors.toList());
        List<String> groupKo = Arrays.stream(environment.getProperty("group.community.ko").split(",")).collect(Collectors.toList());
        m.addAttribute("groupEn", groupEn);
        m.addAttribute("groupKo", groupKo);


        return "introduction/direction";
    }
}
