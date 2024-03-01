package com.example.taskcrud.entity.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ChangeInfoReq {
    private String fullName;
    private String token;
    private String phoneNumber;
    private MultipartFile avatar;
}
