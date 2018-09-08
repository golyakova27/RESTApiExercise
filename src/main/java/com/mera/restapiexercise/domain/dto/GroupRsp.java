package com.mera.restapiexercise.domain.dto;

import com.mera.restapiexercise.model.Group;

import java.io.Serializable;
import java.util.UUID;

public class GroupRsp implements Serializable {
    private UUID id;
    private String name;
    private String description = "Entity not found";

    public GroupRsp() {
    }

    public GroupRsp(Group group) {
        id = group.getId();
        name = group.getName();
        description = group.getDescription();
    }

    public void setGroup(Group group) {
        id = group.getId();
        name = group.getName();
        description = group.getDescription();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
