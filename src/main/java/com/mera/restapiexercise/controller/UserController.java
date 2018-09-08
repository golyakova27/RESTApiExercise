package com.mera.restapiexercise.controller;

import com.mera.restapiexercise.domain.dto.UserRsp;
import com.mera.restapiexercise.exception.ResourceNotFoundException;
import com.mera.restapiexercise.model.User;
import com.mera.restapiexercise.repository.GroupRepository;
import com.mera.restapiexercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
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
        List<UserRsp> usersRsp = new LinkedList<>();
        for (User user : users) {
            usersRsp.add(new UserRsp(user));
        }
        return usersRsp;
    }

    @GetMapping("/user")
    public UserRsp getUser(String uuid, String name){
        // метод аналогичный getGroup()
        UserRsp userRsp = new UserRsp();
        if (uuid != null) {
            try {
                userRsp.setUser(userRepository.getOne(UUID.fromString(uuid)));
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        }
        if (name != null && userRsp.getName() == null) {
            User user = userRepository.getUserByName(name);
            if (user != null) {
                userRsp.setUser(user);
            }
        }
        if (userRsp.getName() == null) throw
                new ResourceNotFoundException("User not found");
        return userRsp;
    }
}
