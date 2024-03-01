package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.Repository.IChannelRepository;
import com.example.taskcrud.Repository.IChatMessageRepository;
import com.example.taskcrud.entity.AppUser;
import com.example.taskcrud.entity.AppUserRole;
import com.example.taskcrud.entity.ChatMessage;
import com.example.taskcrud.entity.request.AuthenticationRequest;
import com.example.taskcrud.entity.request.ChangeInfoReq;
import com.example.taskcrud.entity.request.GetUsersInChannelReq;
import com.example.taskcrud.entity.request.RegisterRequest;
import com.example.taskcrud.entity.response.AuthenticationResponse;
import com.example.taskcrud.entity.response.UpdatePasswordResponse;
import com.example.taskcrud.security.config.JwtService;
import com.example.taskcrud.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IService<AppUser> {
    private final IAppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IChannelRepository channelRepository;
    private final IChatMessageRepository chatMessageRepository;
    public AuthenticationResponse register(RegisterRequest request){
        var x =  repository.findByEmail(request.getEmail());

        var user = AppUser.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .avatar(null)
                .phoneNumber(null)
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
        var user = repository.findByEmail(request.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public String changeUserPassword(UpdatePasswordResponse updatePasswordResponse) {
        var user = repository.findByEmail( updatePasswordResponse.getEmail());
        user.setFullname(updatePasswordResponse.getNewFullname());
        user.setPassWord(passwordEncoder.encode(updatePasswordResponse.getNewPassword()));
        repository.save(user);
        return "success";
    }

    @Override
    public List<AppUser> toList() {
        return repository.findAll();
    }

    @Override
    public AppUser save(AppUser entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(AppUser entity) {
         repository.delete(entity);

    }

    @Override
    public AppUser findById(Long id) {
        return repository.findById(Math.toIntExact(id)).orElse(null);
    }

        public AppUser changeUserInfo(ChangeInfoReq changeInfoReq) {
            var user = JwtUtils.getUserFromToken(jwtService,repository,changeInfoReq.getToken());
            user.setFullname(changeInfoReq.getFullName());
            user.setAvatar(String.valueOf(changeInfoReq.getAvatar()));
            user.setPhoneNumber(changeInfoReq.getPhoneNumber());
            repository.save(user);
            return  user;
        }

    public ArrayList<AppUser> GetUsersInChannel(GetUsersInChannelReq getUsersInChannelReq) {
        var user = JwtUtils.getUserFromToken(jwtService, repository,getUsersInChannelReq.getToken());
        String channelName = getUsersInChannelReq.getName();
        ArrayList<AppUser> usersInChannel = repository.findUsersByChannelName(channelName);
        return usersInChannel;
    }


}
