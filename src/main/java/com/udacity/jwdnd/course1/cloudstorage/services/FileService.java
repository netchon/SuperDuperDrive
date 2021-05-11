package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public File getFileByFileName(String fileName){
        return fileMapper.getFileByFilename(fileName);
    }

    public File getFileById(int fileId){
        return fileMapper.getFileById(fileId);
    }

    public void deletFileById(int fileId){
        fileMapper.deleteFileById(fileId);
    }

    public boolean isFileNameAvailable(String fileName){
        return getFileByFileName(fileName) == null;
    }

    public List<File> getAllFiles(int userId){
        return fileMapper.getAllFiles(userId);
    }

    public int insertFile(MultipartFile file, String username) throws IOException {

        String fileSize = Long.toString(file.getSize());

        User user = userService.getUser(username);

        return fileMapper.insertFile(new File(null,StringUtils.cleanPath(file.getOriginalFilename()),
                file.getContentType(), fileSize, user.getUserId(), file.getBytes()));
    }
}
