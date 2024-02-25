package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.entity.appuser.AppUser;
import com.example.taskcrud.entity.AppUserRole;
import com.example.taskcrud.entity.request.AuthenticationRequest;
import com.example.taskcrud.entity.request.RegisterRequest;
import com.example.taskcrud.entity.response.AuthenticationResponse;
import com.example.taskcrud.entity.response.UpdatePasswordResponse;
import com.example.taskcrud.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IAppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        var x =  repository.findByEmail(request.getEmail());
        if(x.isPresent())
        {
            return AuthenticationResponse.builder().exist(true).noticeIfUserExist("The user is exist in the database").build();
        }
        var user = AppUser.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()) )
                .appUSerRole(AppUserRole.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).noticeIfUserExist("Success register").build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public String changeUserPassword(UpdatePasswordResponse updatePasswordResponse) {
        var user = repository.findByEmail( updatePasswordResponse.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " ));
        user.setFullname(updatePasswordResponse.getNewFullname());
        user.setPassWord(passwordEncoder.encode(updatePasswordResponse.getNewPassword()));
        repository.save(user);
        return "success";
    }
}
