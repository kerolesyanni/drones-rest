package com.dronesapi.drones.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DRONE")
public class Drone {

	@Id
	@Column(name = "serial_no", columnDefinition = "VARCHAR(100) NOT NULL")
	private String serialNumber;

	@Column(name = "model", columnDefinition = "VARCHAR(50) NOT NULL") // Lightweight, Middleweight, Cruiserweight, Heavyweight
	private String model;
	
	@Column(name = "weight_limit", columnDefinition = "VARCHAR(10) NOT NULL")
	private double weightLimit;

	@Column(name = "battery",precision = 3, scale = 2)
	private BigDecimal battery;

	@Column(name = "drone_state", columnDefinition = "VARCHAR(20) NOT NULL") // IDLE, LOADING, LOADED, DELIVERING,
	private String state;
}
