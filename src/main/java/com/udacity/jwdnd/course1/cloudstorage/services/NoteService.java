package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int createNote(Note note){
        return noteMapper.insertNote(note);
    }

    public int updateNote(Note note){
        return noteMapper.updateNote(note);
    }

    public List<Note> getAllNotesByUserId(int userId){
        return noteMapper.getAllNotesByUserId(userId);
    }

    public void deleteNoteByNoteId(int noteId){
        noteMapper.deleteNoteById(noteId);
    }

}
