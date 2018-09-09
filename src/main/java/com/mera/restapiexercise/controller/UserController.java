package com.mera.restapiexercise.controller;

import com.mera.restapiexercise.domain.dto.UserRq;
import com.mera.restapiexercise.domain.dto.UserRsp;
import com.mera.restapiexercise.exception.ResourceNotFoundException;
import com.mera.restapiexercise.model.User;
import com.mera.restapiexercise.repository.GroupRepository;
import com.mera.restapiexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/group/{groupId}/users")
    public List<UserRsp> getUserFromGroup(@PathVariable UUID groupId) {
        List<User> users = userRepository.findByGroupId(groupId);
        List<UserRsp> usersRsp = new ArrayList<>();
        for (User user : users) {
            usersRsp.add(new UserRsp(user));
        }

        return usersRsp;
    }

    @GetMapping("/user")
    public List<UserRsp> getUser(String uuid, String name){
        // метод аналогичный getGroup()
        UserRsp userRsp = new UserRsp();
        List<UserRsp> usersRsp = new ArrayList<>();
        if (uuid != null && userRepository.existsById(UUID.fromString(uuid))) {
            userRsp.setUser(userRepository.getOne(UUID.fromString(uuid)));
        }
        if (userRsp.getName() == null) {
            if (name != null) {
                List<User> users = userRepository.getUserByName(name);
                if (!users.isEmpty()) {
                    for (User user : users) {
                        usersRsp.add(new UserRsp(user));
                    }
                }
            }
        } else {
            usersRsp.add(userRsp);
        }

        if (usersRsp.isEmpty()) throw
                new ResourceNotFoundException("User not found");

        return usersRsp;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRsp addUser(@Valid @RequestBody UserRq user) {

        User new_user = new User();
        new_user.setName(user.getName());

        return groupRepository.findById(user.getGroupId()).map(group -> {
            new_user.setGroup(group);
            return new UserRsp(userRepository.save(new_user));
        }).orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + user.getGroupId()));
    }

    @PutMapping("/user/{userId}")
    public UserRsp updateUser(@PathVariable UUID userId,
                               @Valid @RequestBody UserRq userRq) {
        if(!groupRepository.existsById(userRq.getGroupId())) {
            throw new ResourceNotFoundException("Group not found with id " + userRq.getGroupId());
        }

        return userRepository.findById(userId).map(user -> {
            user.setName(userRq.getName());
            user.setGroup(groupRepository.getOne(userRq.getGroupId()));
            return new UserRsp(userRepository.save(user));
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/group/{groupId}/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID groupId,
                                          @PathVariable UUID userId) {
        if(!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }

        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
