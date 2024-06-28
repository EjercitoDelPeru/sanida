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
    public String homeSubmit(){
        return "/pages/home";
    }
    @RequestMapping("/home2")
    public String home2Submit(){
        return "/page2";
    }
    @RequestMapping("/home3")
    public String home3Submit(){
        return "/page3";
    }

    @RequestMapping("/home4")
    public String home4Submit(){
        return "/page4";
    }

}
