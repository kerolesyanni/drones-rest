package com.dronesapi.drones.model.response;

import com.dronesapi.drones.entity.Drone;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDroneResponse {
	private String status;
	private LocalDateTime timestamp;
	private List<Drone> drones;
}
