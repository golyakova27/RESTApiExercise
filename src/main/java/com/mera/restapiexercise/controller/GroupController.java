package com.mera.restapiexercise.controller;

import com.mera.restapiexercise.domain.dto.GroupGetResponse;
import com.mera.restapiexercise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RestController
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/group")
    public GroupGetResponse getGroup(String uuid, String name){

        GroupGetResponse groupGetResponse = null;

        if (uuid != null) {
            try {
                groupGetResponse = new GroupGetResponse(groupRepository.getOne(UUID.fromString(uuid)));
            }
            catch (EntityNotFoundException e) {
                if (name != null) {
                    groupGetResponse = new GroupGetResponse(groupRepository.getGroupByName(name));
                }
            }
        }

        return groupGetResponse;
    }
}
