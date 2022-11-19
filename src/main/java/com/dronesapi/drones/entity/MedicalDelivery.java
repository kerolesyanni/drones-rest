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
@Table(name = "medical_delivery")
public class MedicalDelivery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "delivery_time", columnDefinition = "TIMESTAMP NOT NULL")
	private LocalDateTime deliveryTime;

	@OneToOne(targetEntity = DroneLoadMedication.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_tracking_id", referencedColumnName = "tracking_id")
	private DroneLoadMedication droneLoadMedication;
}
