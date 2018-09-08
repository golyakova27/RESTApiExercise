package com.mera.restapiexercise.repository;

import com.mera.restapiexercise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByGroupId(UUID groupId);
    User getUserByName(String name);
}
