package com.kh.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // JSP 파일은 src/main/webapp/WEB-INF/jsp/index.jsp 경로에 있어야 함
    }
}
