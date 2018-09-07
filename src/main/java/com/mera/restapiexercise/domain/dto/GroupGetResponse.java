package com.mera.restapiexercise.domain.dto;

import com.mera.restapiexercise.model.Group;

import java.io.Serializable;
import java.util.UUID;

public class GroupGetResponse implements Serializable {
    private UUID uuid;
    private String name;
    private String description;

    public GroupGetResponse() {
    }

    public GroupGetResponse(Group group) {
        uuid = group.getId();
        name = group.getName();
        description = group.getDescription();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
