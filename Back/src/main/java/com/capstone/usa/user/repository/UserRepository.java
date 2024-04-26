package com.capstone.usa.user.repository;

import com.capstone.usa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByPhoneNumber(String phoneNumber);
}
