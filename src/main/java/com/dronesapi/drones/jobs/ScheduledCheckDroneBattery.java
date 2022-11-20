package com.dronesapi.drones.jobs;

import com.dronesapi.drones.entity.Drone;
import com.dronesapi.drones.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DecimalFormat;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ScheduledCheckDroneBattery {
	
	@Autowired
	private DroneRepository droneRepository;
	
	static final Logger log = LoggerFactory.getLogger(ScheduledCheckDroneBattery.class);
	
    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTaskAsync() {
        
        List<Drone> droneList = droneRepository.findAll();
        
    	DecimalFormat decFormat = new DecimalFormat("#%");
        for (Drone arrDroneBatteryLeve : droneList) {
            log.error("Battery level For Drone SerialNumber :-- {} and Battery Level :-- {}", arrDroneBatteryLeve.getSerialNumber(), decFormat.format(arrDroneBatteryLeve.getBattery()));
        }
    }
}
