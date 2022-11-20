package com.dronesapi.drones.repository;

import com.dronesapi.drones.entity.DroneLoadMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoadDroneRepository extends JpaRepository<DroneLoadMedication, String> {
    DroneLoadMedication findByMedicationCode(@Param("code") String code);
    List<DroneLoadMedication> findAllByDroneSerialNumber(@Param("serialNumber") String serialNumber);
}
