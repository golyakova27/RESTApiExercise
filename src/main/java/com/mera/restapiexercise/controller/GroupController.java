package com.mera.restapiexercise.controller;

import com.mera.restapiexercise.domain.dto.GroupRsp;
import com.mera.restapiexercise.exception.NameAlreadyTakenException;
import com.mera.restapiexercise.exception.ResourceNotFoundException;
import com.mera.restapiexercise.model.Group;
import com.mera.restapiexercise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/group")
    public GroupRsp getGroup(String uuid, String name){

        // GroupRsp для ответов на запросы
        GroupRsp groupRsp = new GroupRsp();

        // обрабатываю исключение, чтобы при неверно введённом uuid или его отсутствии
        // была возможность продолжать искать по имени группы
        if (uuid != null) {
            try {
                groupRsp.setGroup(groupRepository.getOne(UUID.fromString(uuid)));
            } catch (EntityNotFoundException e) {
                System.out.println(e);
            }
        }
        // если введено имя группы и по uuid ничего в БД не найдено
        if (name != null && groupRsp.getName() == null) {
            Group group = groupRepository.getGroupByName(name);
            if (group != null) {
                groupRsp.setGroup(group);
            }
        }
        // если группа не была найдена по uuid или имени
        if (groupRsp.getName() == null) throw
                new ResourceNotFoundException("Group not found");

        return groupRsp;
    }

    @PostMapping("/group")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupRsp createGroup(@Valid @RequestBody Group group) {

        if (groupRepository.getGroupByName(group.getName()) != null) {
            throw new NameAlreadyTakenException("This name is already in use");
        }
        Group new_group = groupRepository.save(group);
        System.out.println(new_group);
        return new GroupRsp(new_group);
    }

    @PutMapping("/group/{groupId}")
    public GroupRsp updateGroup(@PathVariable UUID groupId,
                                @Valid @RequestBody Group groupRequest) {

        if (groupRepository.getGroupByName(groupRequest.getName()) != null) {
            throw new NameAlreadyTakenException("This name is already in use");
        }
        return groupRepository.findById(groupId)
                .map(group -> {
                    group.setName(groupRequest.getName());
                    group.setDescription(groupRequest.getDescription());
                    return new GroupRsp(groupRepository.save(group)) ;
                }).orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable UUID groupId) {
        return groupRepository.findById(groupId)
                .map(group -> {
                    groupRepository.delete(group);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Group not found with id " + groupId));
    }
}
