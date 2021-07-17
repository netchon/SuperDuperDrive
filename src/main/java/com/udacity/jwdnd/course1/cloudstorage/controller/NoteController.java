package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private UserService userService;


    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public ModelAndView createOrUpdateNote(@ModelAttribute Note note, Model model, Authentication authentication){
        note.setUserId(userService.getUserIdByUserName(authentication.getName()));
        try {
            if(note.getNoteId() == null){
                int result = noteService.createNote(note);
                if(result == -1){
                    model.addAttribute("error", true);
                    model.addAttribute("message", "Note already available");
                }else{
                    model.addAttribute("success", true);
                    model.addAttribute("message", "Note inserted successfully");
                }
            }else{
                    noteService.updateNote(note);
                    model.addAttribute("success", true);
                    model.addAttribute("message", "Note updated successfully");
            }

        }catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "error while saving note " + e.getCause());
        }
        return new ModelAndView("result");
    }

    @GetMapping("/delete/{noteId}")
    public ModelAndView deleteNote(@PathVariable("noteId") int noteId, Model model){
        try{
            noteService.deleteNoteByNoteId(noteId);
            model.addAttribute("success", true);
            model.addAttribute("message", "note deleted successfully");
        }catch(Exception e){
            model.addAttribute("error", true);
            model.addAttribute("message", "error deleting note");
        }

        return new ModelAndView("result");
    }
}
