package io.zkrytonite.chakra.repositories;

import io.zkrytonite.chakra.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
