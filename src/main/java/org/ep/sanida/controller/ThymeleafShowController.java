package org.ep.sanida.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafShowController {
        @RequestMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
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

    @RequestMapping("/home5")
    public String home5Submit(){
        return "/page5";
    }

    @RequestMapping("/home6")
    public String home6Submit(){
        return "/page6";
    }

    
    @RequestMapping("/home7")
    public String home7Submit(){
        return "/page7";
    }

    @RequestMapping("/home8")
    public String home8Submit(){
        return "/page8";
    }
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafShowController.class);

      @GetMapping("/mensaje")
  public ResponseEntity<?> getMensaje() {
    logger.info("Obteniendo el mensaje");
 
    var auth =  SecurityContextHolder.getContext().getAuthentication();
    logger.info("Datos del Usuario: {}", auth.getPrincipal());
    logger.info("Username del Usuario: {}", auth.getName() );
    logger.info("Datos de los Roles {}", auth.getAuthorities());
    logger.info("Esta autenticado {}", auth.isAuthenticated());

    Map<String, String> mensaje = new HashMap<>();
    mensaje.put("contenido", "Hola Peru");
    return ResponseEntity.ok(mensaje);
  }

}
