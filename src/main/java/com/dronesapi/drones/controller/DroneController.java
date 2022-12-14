package com.dronesapi.drones.controller;

import com.dronesapi.drones.exception.BadRequestException;
import com.dronesapi.drones.model.request.DroneSerialNumberRequest;
import com.dronesapi.drones.model.request.DroneRegisterRequest;
import com.dronesapi.drones.model.request.LoadDroneRequest;
import com.dronesapi.drones.model.response.AvailableDroneResponse;
import com.dronesapi.drones.model.response.DroneBatteryDetailsResponse;
import com.dronesapi.drones.model.response.DroneMedicationLoadResponse;
import com.dronesapi.drones.model.response.DroneResponse;
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
    public ResponseEntity<DroneResponse> registerDrone(@RequestBody DroneRegisterRequest droneRequest) throws BadRequestException {
        validateDrone(droneRequest.getSerialNumber(), droneRequest.getWeightLimit());
        DroneResponse newDrone = droneService.register(droneRequest);
        return new ResponseEntity<>(newDrone, HttpStatus.OK);
    }

    @GetMapping(path= "/available", produces = "application/json")
    public ResponseEntity<AvailableDroneResponse> getAvailableDroneForLoading() {
        AvailableDroneResponse drones = droneService.getAvailableDrones();
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }
    @PostMapping(path = "/battery", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneBatteryDetailsResponse> checkDroneBattery(@RequestBody() DroneSerialNumberRequest batteryRequest) {
        if (batteryRequest.getSerialNumber() == null || batteryRequest.getSerialNumber().isEmpty()) {
            throw new RuntimeException("SerialNumber is Required");
        }
        DroneBatteryDetailsResponse droneBattery = droneService.getBatteryLevel(batteryRequest);
        return new ResponseEntity<>(droneBattery, HttpStatus.OK);
    }

    @PostMapping(path = "/load", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneResponse> loadDroneWithMedication(@RequestBody LoadDroneRequest loadRequest) {
        DroneResponse loadDrone = droneService.loadDrone(loadRequest);
        return new ResponseEntity<>(loadDrone, HttpStatus.OK);
    }

    @GetMapping(path = "details/{serialNumber}", produces = "application/json")
    public ResponseEntity<DroneMedicationLoadResponse> checkLoadedMedicationItem(@PathVariable("serialNumber") String serialNumber) {
        DroneMedicationLoadResponse droneLoads = droneService.getLoadedMedicationForADrone(serialNumber);
        return new ResponseEntity<>(droneLoads, HttpStatus.OK);
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
