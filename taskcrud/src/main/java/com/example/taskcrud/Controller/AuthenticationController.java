package com.example.taskcrud.Controller;

import com.example.taskcrud.entity.response.UpdatePasswordResponse;
import com.example.taskcrud.entity.request.AuthenticationRequest;
import com.example.taskcrud.entity.response.AuthenticationResponse;
import com.example.taskcrud.service.AuthenticationService;
import com.example.taskcrud.entity.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {
        var authenticationResponse = authenticationService.register(request);

        return ResponseEntity.ok(authenticationResponse);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/demo")
    public ResponseEntity<String> authenticate(){
        return ResponseEntity.ok("Sucess");
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<String> changeUserPassword(@RequestBody UpdatePasswordResponse updatePasswordResponse) {

      return  ResponseEntity.ok(authenticationService.changeUserPassword(updatePasswordResponse));
    }
}
