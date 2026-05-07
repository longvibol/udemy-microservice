package com.app.ecom.service.user;

import com.app.ecom.dto.address.AddressDTO;
import com.app.ecom.dto.user.UserRequest;
import com.app.ecom.dto.user.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> fethAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromUserRequest(user, userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> fethUser(Long id) {

        return userRepository.findById(id).map(this::mapToUserResponse);

    }

    public boolean updateUser(Long id, UserRequest updateUserRequest){
        return userRepository.findById(id)
                .map( user -> {
                    updateUserFromUserRequest(user,updateUserRequest);
                    userRepository.save(user);
                    return true;
                }).orElse( false);
    }

    private UserResponse mapToUserResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        if (user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());

            response.setAddress(addressDTO);

        }

        return response;
    }

    // convert from UserRequest to save in database
    private void updateUserFromUserRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null){
            Address address = new Address();

            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);

        }

    }
}
