package com.example.demo.repository;

import com.example.demo.entity.PlayGround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayGroundRepository extends JpaRepository<PlayGround, Integer> {
}
