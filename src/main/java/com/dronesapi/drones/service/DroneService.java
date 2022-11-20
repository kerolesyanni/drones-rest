package com.dronesapi.drones.service;

import com.dronesapi.drones.entity.Drone;
import com.dronesapi.drones.model.request.DroneGetBatteryRequest;
import com.dronesapi.drones.model.request.DroneRegisterRequest;
import com.dronesapi.drones.model.response.AvailableDroneResponse;
import com.dronesapi.drones.model.response.DroneBatteryDetailsResponse;
import com.dronesapi.drones.model.response.RegisterDroneResponse;
import com.dronesapi.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DroneService {

	@Autowired
	private DroneRepository droneRepository;

	public RegisterDroneResponse register(DroneRegisterRequest droneRequest) {
		Drone newDrone = new Drone();
		newDrone.setSerialNumber(droneRequest.getSerialNumber());
		newDrone.setModel(droneRequest.getModel());
		newDrone.setWeightLimit(droneRequest.getWeightLimit());
		newDrone.setBattery(droneRequest.getBattery());
		newDrone.setState(droneRequest.getState());
		droneRepository.save(newDrone);

		RegisterDroneResponse droneResponse = new RegisterDroneResponse();
		droneResponse.setResult("success");
		droneResponse.setSerialNumber(newDrone.getSerialNumber());
		droneResponse.setMessage("New Drone created successfully");
		droneResponse.setTimestamp(LocalDateTime.now());

		return droneResponse;
	}

	public AvailableDroneResponse getAvailableDrones() {
		List<Drone> drones = droneRepository.findAllByState("IDLE");
		return new AvailableDroneResponse("status", LocalDateTime.now(), drones);
	}

	public DroneBatteryDetailsResponse getBatteryLevel(DroneGetBatteryRequest request) {

		Drone newDrone = new Drone();
		DecimalFormat decFormat = new DecimalFormat("#%");
		DroneBatteryDetailsResponse batteryDetailsResponse = new DroneBatteryDetailsResponse();
		newDrone.setSerialNumber(request.getSerialNumber());
		Drone droneBattery = droneRepository.findBySerialNumber(newDrone.getSerialNumber());
		if (droneBattery == null) {
			throw new RuntimeException("Drone battery level details not found");
		}

		batteryDetailsResponse.setStatus("success");
		batteryDetailsResponse.setSerialNumber(droneBattery.getSerialNumber());
		batteryDetailsResponse.setBattery(decFormat.format(droneBattery.getBattery()));
		batteryDetailsResponse.setTimestamp(LocalDateTime.now());

		return batteryDetailsResponse;
	}
}
