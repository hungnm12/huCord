package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.Repository.IFileRepository;
import com.example.taskcrud.entity.File;
import com.example.taskcrud.security.config.JwtService;
import com.example.taskcrud.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class FileService implements IService<File>{
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

    @Override
    public List<File> toList() {
        return fileRepository.findAll();
    }

    @Override
    public File save(File entity) {
        return fileRepository.save(entity);
    }

    @Override
    public void delete(File entity) {
        fileRepository.delete(entity);
    }

    @Override
    public File findById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }
}
