package com.mera.restapiexercise.repository;

import com.mera.restapiexercise.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    Group getGroupByName(String name);
}
