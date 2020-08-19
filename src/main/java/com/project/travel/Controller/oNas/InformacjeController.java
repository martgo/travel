package com.project.travel.Controller.oNas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformacjeController {
    @GetMapping ("/informacje")
    public String informacje(){
        return "informacje";
    }
}
