package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.Repository.IFileRepository;
import com.example.taskcrud.entity.File;
import com.example.taskcrud.entity.appuser.AppUser;
import com.example.taskcrud.security.config.JwtService;
import com.example.taskcrud.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
@Slf4j
public class FileService {
    @Autowired
    private IFileRepository fileRepository;
    @Autowired
    private IAppUserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public String updloadFile(MultipartFile file, String token) throws IOException {

        var user = JwtUtils.getUserFromToken(jwtService,userRepository,token);
        File file1 = File.builder().originalFilename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .uploadedBy(user)
                .build();
        fileRepository.save(file1);

        return "File upload successfully";

    }
}
