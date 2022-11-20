package com.dronesapi.drones;

import com.dronesapi.drones.entity.Drone;
import com.dronesapi.drones.repository.DroneRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class RepositoryTests {
	@Autowired
	DroneRepository droneRepository;

	@Test
	public void testAddNewDrone() {
		Drone drone = new Drone("KY123", "Lightweight", 500.0, new BigDecimal("0.95"), "IDLE");
		droneRepository.save(drone);

		Drone droneDb = droneRepository.findBySerialNumber("KY123");
	    Assertions.assertThat(droneDb.getSerialNumber()).isEqualTo(drone.getSerialNumber());
	    droneRepository.deleteAll();
	    Assertions.assertThat(droneRepository.findAll()).isEmpty();

	}
}
