package com.dronesapi.drones.service;

import com.dronesapi.drones.entity.Drone;
import com.dronesapi.drones.entity.DroneLoadMedication;
import com.dronesapi.drones.entity.Medication;
import com.dronesapi.drones.model.request.DroneSerialNumberRequest;
import com.dronesapi.drones.model.request.DroneRegisterRequest;
import com.dronesapi.drones.model.request.LoadDroneRequest;
import com.dronesapi.drones.model.response.AvailableDroneResponse;
import com.dronesapi.drones.model.response.DroneBatteryDetailsResponse;
import com.dronesapi.drones.model.response.DroneMedicationLoadResponse;
import com.dronesapi.drones.model.response.DroneResponse;
import com.dronesapi.drones.repository.DroneRepository;
import com.dronesapi.drones.repository.LoadDroneRepository;
import com.dronesapi.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private LoadDroneRepository loadDroneRepository;

    public DroneResponse register(DroneRegisterRequest droneRequest) {
        Drone newDrone = new Drone();
        newDrone.setSerialNumber(droneRequest.getSerialNumber());
        newDrone.setModel(droneRequest.getModel());
        newDrone.setWeightLimit(droneRequest.getWeightLimit());
        newDrone.setBattery(droneRequest.getBattery());
        newDrone.setState(droneRequest.getState());
        droneRepository.save(newDrone);

        DroneResponse droneResponse = new DroneResponse();
        droneResponse.setResult("Success");
        droneResponse.setSerialNumber(newDrone.getSerialNumber());
        droneResponse.setMessage("New Drone created successfully");
        droneResponse.setTimestamp(LocalDateTime.now());

        return droneResponse;
    }

    public AvailableDroneResponse getAvailableDrones() {
        List<Drone> drones = droneRepository.findAllByState("IDLE");
        return new AvailableDroneResponse("Success", LocalDateTime.now(), drones);
    }

    public DroneBatteryDetailsResponse getBatteryLevel(DroneSerialNumberRequest request) {

        Drone newDrone = new Drone();
        DecimalFormat decFormat = new DecimalFormat("#%");
        DroneBatteryDetailsResponse batteryDetailsResponse = new DroneBatteryDetailsResponse();
        newDrone.setSerialNumber(request.getSerialNumber());
        Drone droneBattery = droneRepository.findBySerialNumber(newDrone.getSerialNumber());
        if (droneBattery == null) {
            throw new RuntimeException("Drone battery level details not found");
        }

        batteryDetailsResponse.setStatus("Success");
        batteryDetailsResponse.setSerialNumber(droneBattery.getSerialNumber());
        batteryDetailsResponse.setBattery(decFormat.format(droneBattery.getBattery()));
        batteryDetailsResponse.setTimestamp(LocalDateTime.now());

        return batteryDetailsResponse;
    }

    public DroneResponse loadDrone(LoadDroneRequest loadRequest) {

        droneRepository.setUpdateState("LOADING", loadRequest.getSerialNumber());
        Drone drone = droneRepository.findBySerialNumber(loadRequest.getSerialNumber());
        Medication medication = medicationRepository.findByCode(loadRequest.getCode());
        DroneLoadMedication checkLoad = loadDroneRepository.findByMedicationCode(loadRequest.getCode());
        List<DroneLoadMedication> droneLoadMedicationList = loadDroneRepository.findAllByDroneSerialNumber(loadRequest.getSerialNumber());

        if (medication == null) {
            throw new RuntimeException("Medication specified does not exist");
        }

        double droneLoadWeight = droneLoadMedicationList
                .stream()
                .mapToDouble(droneLoadMedication -> droneLoadMedication.getMedication().getWeight())
                .sum() + medication.getWeight();

        System.out.println("droneLoadWeight " + droneLoadWeight);

        if (checkLoad != null) {
            throw new RuntimeException("Medication code has already been loaded, try another one");

        }

        // validate before loading
        if (drone == null) {
            throw new RuntimeException("Drone specified does not exist");
        }


        if (drone.getWeightLimit() < medication.getWeight() || drone.getWeightLimit() < droneLoadWeight) {
            throw new RuntimeException("The Drone cannot load more than the weight limit");
        }
        // check battery
        if (drone.getBattery().compareTo(new BigDecimal("0.25")) < 0) {
            throw new RuntimeException("The Drone cannot be loaded, battery below 25%");
        }
        // load
        DroneLoadMedication loadMedication = new DroneLoadMedication();
        loadMedication.setDrone(drone);
        loadMedication.setMedication(medication);
        loadMedication.setSource(loadRequest.getSource());
        loadMedication.setDestination(loadRequest.getDestination());
        loadMedication.setCreatedOn(LocalDateTime.now());
        loadDroneRepository.save(loadMedication);
        droneRepository.setUpdateState("LOADED", loadRequest.getSerialNumber());

        DroneResponse loadDroneResponse = new DroneResponse();
        loadDroneResponse.setResult("success");
        loadDroneResponse.setSerialNumber(loadRequest.getSerialNumber());
        loadDroneResponse.setMessage("Drone Loaded successfully");
        loadDroneResponse.setTimestamp(LocalDateTime.now());

        return loadDroneResponse;
    }

    public DroneMedicationLoadResponse getLoadedMedicationForADrone(String serialNumber) {

        List<DroneLoadMedication> droneLoadMedicationList = loadDroneRepository.findAllByDroneSerialNumber(serialNumber);
        if (droneLoadMedicationList == null || droneLoadMedicationList.isEmpty()) {
            throw new RuntimeException("No load Medication details for the specified drone");
        }
        DroneMedicationLoadResponse droneLoad = new DroneMedicationLoadResponse();
        droneLoad.setResult("success");
        droneLoad.setSerialNumber(droneLoadMedicationList.get(0).getDrone().getSerialNumber());
        droneLoad.setTimestamp(LocalDateTime.now());
        List<Medication> medications = droneLoadMedicationList
                .stream()
                .map(DroneLoadMedication::getMedication)
                .collect(Collectors.toList());
        droneLoad.setMedications(medications);

        return droneLoad;
    }
}
