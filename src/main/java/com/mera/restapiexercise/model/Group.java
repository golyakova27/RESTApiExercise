package com.mera.restapiexercise.model;

import java.util.List;
import java.util.UUID;

public class Group {
    private UUID id;
    private String name;
    private String description;
    private List<User> users;

    public Group() {}

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "Group[id: " + id +
                ",name: " + name +
                ",description: " + description +
                "]";
    }
}
