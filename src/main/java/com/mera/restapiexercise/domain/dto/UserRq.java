package com.mera.restapiexercise.domain.dto;

import java.io.Serializable;
import java.util.UUID;

public class UserRq implements Serializable {
    private String name;
    private UUID groupId;

    public UserRq() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
}
