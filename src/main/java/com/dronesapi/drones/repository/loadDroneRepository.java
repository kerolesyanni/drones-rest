package com.dronesapi.drones.repository;

import com.dronesapi.drones.entity.DroneLoadMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface loadDroneRepository extends JpaRepository<DroneLoadMedication, String> {
}
