package org.ep.sanida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafShowController {
        @RequestMapping("/")
    public String loginPage(){
        return "index";
    }

    @RequestMapping("/home")
    public String loginSubmit(){
        return "/pages/home";
    }
}
