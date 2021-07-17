package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping
    public ModelAndView createOrUpdateCredential(@ModelAttribute Credential credential, Model model,
                                                 Authentication authentication){
        credential.setUserId(userService.getUserIdByUserName(authentication.getName()));
        try{
            if(credential.getCredentialId() == null) {
                int result = credentialService.saveCredential(credential);
                if(result == -1){
                    model.addAttribute("error", true);
                    model.addAttribute("message", "user already available");
                }else {
                    model.addAttribute("success", true);
                    model.addAttribute("message", "credential saved successfully");
                }
            }else{
                credentialService.updateCredential(credential);
                model.addAttribute("success", true);
                model.addAttribute("message", "credential updated successfully");
            }


        }catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "error saving credentials");

        }
        return new ModelAndView("result");
    }

    @GetMapping("/delete/{credentialId}")
    public ModelAndView deleteCredential(@PathVariable("credentialId") int credentialId, Model model){
        try {
            credentialService.deleteCredentialById(credentialId);
            model.addAttribute("success", true);
            model.addAttribute("message", "credential deleted successfully");
        }catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "error deleting credentials");
        }
        return new ModelAndView("result");
    }

    @GetMapping(value = "/decodedData", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Map<String,String> decodedData(@RequestParam int credentialId){
        String decryptedValue = credentialService.decryptedData(credentialId);
        Map<String,String> map = new HashMap<>();
        map.put("decryptedData", decryptedValue);
        return map;
    }
}
