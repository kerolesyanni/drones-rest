package com.dronesapi.drones.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneGetBatteryRequest {
	private String serialNumber;
}
