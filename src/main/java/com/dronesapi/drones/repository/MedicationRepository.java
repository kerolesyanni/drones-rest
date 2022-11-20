package com.dronesapi.drones.repository;

import com.dronesapi.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MedicationRepository extends JpaRepository<Medication, String> {

    Medication findByCode(@Param("id") String id);
}
