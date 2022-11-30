package io.zkrytonite.chakra.repositories;

import io.zkrytonite.chakra.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);
    boolean existsUserByEmail(String email);
}
