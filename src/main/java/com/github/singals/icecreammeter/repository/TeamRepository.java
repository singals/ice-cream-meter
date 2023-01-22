package com.github.singals.icecreammeter.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.singals.icecreammeter.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    
}
