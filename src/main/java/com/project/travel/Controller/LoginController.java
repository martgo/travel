package com.project.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("konto", new Account());
        return "login";
    }


    @PostMapping("/login")
    public String verifyLoginPage(@ModelAttribute Account account, Model model) {
        model.addAttribute("konto", new Account());
        if (account.getLogin().equals("marta") && account.getHaslo().equals("IT"))
            return "/kierunki";
        return "errorLogin";
    }
}
