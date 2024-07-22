package com.example.spingsercurewebservicev2;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);
}
