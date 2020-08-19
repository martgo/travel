package com.project.travel.Controller.oNas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DaneOsoboweController {
    @GetMapping("/daneosobowe")
    public String daneosobowe(){
        return "daneosobowe";
    }
}
