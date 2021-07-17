package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int createNote(Note note){
        boolean result = checkIfNoteAlreadyExist(note);
        if(result)
            return -1;

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

    private boolean checkIfNoteAlreadyExist(Note n){
        Optional<Note> note = Optional.ofNullable(noteMapper.getNoteByTitle(n.getNoteTitle()));
        if(note.isPresent()){
            if(note.get().getNoteDescription().equals(n.getNoteDescription()))
                return true;
        }

        return false;
    }

}
