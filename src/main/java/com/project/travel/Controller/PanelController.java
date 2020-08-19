package com.project.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String panelPage() {
        return "panel";
    }
}


