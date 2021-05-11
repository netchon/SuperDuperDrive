package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<ByteArrayResource> viewFile(@PathVariable("fileId") int fileId){
     File file = fileService.getFileById(fileId);
     ByteArrayResource byteArrayResource = new ByteArrayResource(file.getFileData());
     return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
             .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+
                     file.getFileName()+"\"").body(byteArrayResource);
    }

    @GetMapping("/delete/{fileId}")
    public String deletFile(@PathVariable("fileId") int fileId, RedirectAttributes redirectAttributes){
        try {
            fileService.deletFileById(fileId);
            redirectAttributes.addAttribute("fileSuccess", true);
            redirectAttributes.addAttribute("fileMessage", "file deleted sucessfully");
            return "redirect:/home";

        }catch(Exception e){
            redirectAttributes.addAttribute("fileError", true);
            redirectAttributes.addAttribute("fileMessage", "error deleting a file");
            return "redirect:/home";
        }

    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile file,
                             RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {

        if(file.isEmpty()) {
            redirectAttributes.addAttribute("fileError", true);
            redirectAttributes.addAttribute("fileMessage", "please select a file to upload");
            return "redirect:/home";
        }

        if(!fileService.isFileNameAvailable(StringUtils.cleanPath(file.getOriginalFilename()))){
            redirectAttributes.addAttribute("fileError", true);
            redirectAttributes.addAttribute("fileMessage", "please provide another name to the file");
            return "redirect:/home";
        }

        try {
            String username = authentication.getName();
            fileService.insertFile(file, username);
            redirectAttributes.addAttribute("fileSuccess", true);
            redirectAttributes.addAttribute("fileMessage", "file added successfully");
            return "redirect:/home";
        }catch (Exception e){
            redirectAttributes.addAttribute("fileError", true);
            redirectAttributes.addAttribute("fileMessage", "error adding file");
        }

        return "redirect:/home";
    }
}
