package org.azerciard.userservice.repository;

import jakarta.transaction.Transactional;
import org.azerciard.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
