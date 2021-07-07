package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;
    private HashService hashService;

    public SignupController(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public RedirectView signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;

        if(!userService.isUsernameAvailable(user.getUsername()))
            signupError = "username not avaibale, please provide another one";

        if(signupError == null){
            int result = userService.createUser(user);
            if(result < 0)
                signupError = "please try again, error submitting a new user";
        }

        if(signupError == null){
            model.addAttribute("signupSuccess", true);
        }else{
            model.addAttribute("signupError", signupError);
        }

        return new RedirectView("login");
    }

}