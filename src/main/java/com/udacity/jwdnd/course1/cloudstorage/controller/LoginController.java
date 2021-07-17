package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public ModelAndView loginView(
            ModelMap model,
            @ModelAttribute("successSignUp") Object flashAttribute) {

        boolean msm = false;

        if(Boolean.parseBoolean(String.valueOf(flashAttribute)))
            msm=true;

        model.addAttribute("message", msm);

        return new ModelAndView("login", model);
    }
}
