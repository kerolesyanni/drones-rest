package com.dronesapi.drones.controller;

import com.dronesapi.drones.exception.BadRequestException;
import com.dronesapi.drones.model.request.DroneRegisterRequest;
import com.dronesapi.drones.model.response.RegisterDroneResponse;
import com.dronesapi.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drone")
@Validated
public class DroneController {
    @Autowired
    private DroneService droneService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RegisterDroneResponse> registerDrone(@RequestBody DroneRegisterRequest droneRequest) throws BadRequestException {
        validateDrone(droneRequest.getSerialNumber(), droneRequest.getWeightLimit());
        RegisterDroneResponse newDrone = droneService.register(droneRequest);
        return new ResponseEntity<>(newDrone, HttpStatus.OK);
    }

    private void validateDrone(String serialNumber, double weightLimit) throws BadRequestException {
        if (serialNumber.length() > 100) {
            throw new BadRequestException("Please enter name with max character 100");
        }
        if (weightLimit > 500) {
            throw new BadRequestException("Please enter weight for drone that not exceed 500gr");
        }

    }
}
