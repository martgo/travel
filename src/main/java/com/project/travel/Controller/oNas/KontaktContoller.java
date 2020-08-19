package com.project.travel.Controller.oNas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class KontaktContoller {
   @GetMapping("/kontakt")
    public String kontakt(){
        return "kontakt";
    }
}
