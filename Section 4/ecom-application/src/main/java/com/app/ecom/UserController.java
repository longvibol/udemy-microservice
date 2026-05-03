package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
//    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.fethAllUsers(), HttpStatus.OK);
//        return ResponseEntity.ok(userService.fethAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUsers(@PathVariable Long id){

        return userService.fethUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser1(@PathVariable Long id, @RequestBody User updateUser){

        return userService.updateUser1(id,updateUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updateUser){
        boolean update = userService.updateUser(id, updateUser);
        if (update)
            return ResponseEntity.ok("User update successfully");
        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User Added Successfully");
    }
}
