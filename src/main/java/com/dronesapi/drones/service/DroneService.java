package com.dronesapi.drones.service;

import com.dronesapi.drones.entity.Drone;
import com.dronesapi.drones.model.request.DroneRegisterRequest;
import com.dronesapi.drones.model.response.RegisterDroneResponse;
import com.dronesapi.drones.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
