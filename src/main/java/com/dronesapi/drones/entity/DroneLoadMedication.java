package com.dronesapi.drones.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drone_load")
public class DroneLoadMedication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tracking_id")
	private Integer trackingId;

	@Column(name = "source", columnDefinition = "VARCHAR(30) NOT NULL")
	private String source;

	@Column(name = "destination", columnDefinition = "VARCHAR(30) NOT NULL")
	private String destination;

	@Column(name = "created_on", columnDefinition = "VARCHAR(30) NOT NULL")
	private LocalDateTime createdOn;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_serial_no", referencedColumnName = "serial_no")
	private Drone drone;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_code", referencedColumnName = "code", unique = true)
	private Medication medication;
}
