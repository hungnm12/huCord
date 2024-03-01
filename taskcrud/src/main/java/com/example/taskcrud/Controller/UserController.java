package com.example.taskcrud.Controller;

import com.example.taskcrud.entity.AppUser;
import com.example.taskcrud.entity.request.ChangeInfoReq;
import com.example.taskcrud.entity.request.GetUsersInChannelReq;
import com.example.taskcrud.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/getuser")
    public ResponseEntity<AppUser> findById(@RequestParam Long id) throws IOException {
        try {
            AppUser user = authenticationService.findById(id);
            return ResponseEntity.ok(user);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/info") // Endpoint for updating user information
    public ResponseEntity<AppUser> changeUserInfo(@RequestBody ChangeInfoReq changeInfoReq) throws IOException {
        try {
                AppUser user = authenticationService.changeUserInfo(changeInfoReq);


            return ResponseEntity.ok(user); // Return success message
        } catch (Exception e) {
            // Handle errors gracefully
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/channels/users/{channelName}") // Endpoint for getting users in a channel
    public ResponseEntity<List<AppUser>> GetUsersInChannel(@PathVariable GetUsersInChannelReq req) {
        try {
            // Find users in the specified channel
            List<AppUser> usersInChannel = authenticationService.GetUsersInChannel(req);

            return ResponseEntity.ok(usersInChannel); // Return list of users
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle errors gracefully
        }
    }
}
