package com.mera.restapiexercise.domain.dto;

import com.mera.restapiexercise.model.User;

import java.io.Serializable;
import java.util.UUID;

public class UserRsp implements Serializable {

    private UUID id;
    private String name;
    private String group;

    public UserRsp() {}

    public UserRsp(User user) {
        id = user.getId();
        name = user.getName();
        group = user.getGroup().getName();
    }

    public void setUser(User user) {
        id = user.getId();
        name = user.getName();
        group = user.getGroup().getName();
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
