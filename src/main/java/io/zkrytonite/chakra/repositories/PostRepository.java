package io.zkrytonite.chakra.repositories;

import io.zkrytonite.chakra.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
