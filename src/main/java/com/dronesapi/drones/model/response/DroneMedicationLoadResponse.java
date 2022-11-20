package com.dronesapi.drones.model.response;

import com.dronesapi.drones.entity.Medication;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneMedicationLoadResponse {

	private String result;
	private String serialNumber;
	private LocalDateTime timestamp;
	List<Medication> medications;
}
