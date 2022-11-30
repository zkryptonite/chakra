package io.zkrytonite.chakra.repositories;

import io.zkrytonite.chakra.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
