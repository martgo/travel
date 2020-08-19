package com.project.travel.Controller.Bezpieczne;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZglosController {
    @GetMapping("/zglos")
    public String zglosPage() {
        return "zglos";
    }
}
