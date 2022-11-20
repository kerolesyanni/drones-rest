package com.dronesapi.drones.repository;

import com.dronesapi.drones.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    Drone findBySerialNumber(@Param("id") String id);
}
