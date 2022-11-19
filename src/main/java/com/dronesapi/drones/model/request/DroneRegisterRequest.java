package com.dronesapi.drones.model.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneRegisterRequest {


	private String serialNumber;
	private String model;
	private double weightLimit;
	private BigDecimal battery;
	private String state;
}
