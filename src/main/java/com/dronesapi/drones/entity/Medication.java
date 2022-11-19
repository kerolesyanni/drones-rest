package com.dronesapi.drones.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medication")
public class Medication {

	@Id
	@Column(name = "code", columnDefinition = "VARCHAR(16) NOT NULL")
	private String code;

	@Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL")
	private String name;

	@Column(name = "weight", columnDefinition = "VARCHAR(10) NOT NULL")
	private double weight;

	@Column(name = "image", columnDefinition = "VARCHAR(10) NOT NULL")
	private String image;
}
