package com.app.ecom.controller.user;

import com.app.ecom.dto.user.UserRequest;
import com.app.ecom.dto.user.UserResponse;
import com.app.ecom.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fethAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added Successfully");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest updateUserRequest){
        boolean update = userService.updateUser(id, updateUserRequest);
        if (update)
            return ResponseEntity.ok("User update successfully");
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getAllUsers(@PathVariable Long id){

        return userService.fethUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
