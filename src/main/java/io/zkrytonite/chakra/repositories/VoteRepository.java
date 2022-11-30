package io.zkrytonite.chakra.repositories;

import io.zkrytonite.chakra.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
