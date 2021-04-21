package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {


    @GetMapping("/landing")
    public String onIndexRequest()
    {
        return "landing";
    }
}
