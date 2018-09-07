package com.mera.restapiexercise.model;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private Group group;

    public User() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
