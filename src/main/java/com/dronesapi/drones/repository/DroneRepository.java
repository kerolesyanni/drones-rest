package com.dronesapi.drones.repository;

import com.dronesapi.drones.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DroneRepository extends JpaRepository<Drone, String> {
    Drone findBySerialNumber(@Param("id") String id);

    List<Drone> findAllByState(@Param("state") String state);

    @Modifying
    @Query(value = "update Drone d set d.state = :state where d.serialNumber= :serialNo")
    void setUpdateState (@Param("state") String status, @Param("serialNo") String serialNo);
}
