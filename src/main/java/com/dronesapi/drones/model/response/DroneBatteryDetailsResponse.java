package com.dronesapi.drones.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryDetailsResponse {

	private String status;
	private String serialNumber;
	private String battery;
	private LocalDateTime timestamp;
}
