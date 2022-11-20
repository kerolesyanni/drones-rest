package com.dronesapi.drones.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneSerialNumberRequest {
	private String serialNumber;
}
