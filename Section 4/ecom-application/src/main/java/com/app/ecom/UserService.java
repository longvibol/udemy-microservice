package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> userList = new ArrayList<>();
    private Long userId = 1L;

    public List<User> fethAllUsers(){
        return userList;
    }

    public void addUser(User user){
        user.setId(userId++);
        userList.add(user);
    }

    public Optional<User> fethUser(Long id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> updateUser1(Long id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    return user;
                });
    }

    public boolean updateUser(Long id, User updateUser){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map( user -> {
                    user.setFirstName(updateUser.getFirstName());
                    user.setLastName(updateUser.getLastName());
                    return true;
                }).orElse( false);
    }
}
