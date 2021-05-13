package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(Model model, Authentication authentication){
        model.addAttribute("files", fileService.getAllFiles(
                userService.getUserIdByUserName(authentication.getName())));

        model.addAttribute("notes", noteService.getAllNotesByUserId(userService.
                getUserIdByUserName(authentication.getName())));

        model.addAttribute("credentials", credentialService.
                getAllCredentialsByUserId(userService.getUserIdByUserName(authentication.getName())));

        return "home";
    }
}
