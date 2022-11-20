package com.dronesapi.drones.model.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneRequest {
	private String serialNumber;
	private String source;
	private String destination;
	private String code;
}
