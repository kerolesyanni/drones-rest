package com.dronesapi.drones.model.response;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DroneBatteryDetailsResponse {

	private String status;
	private String serialNumber;
	private String battery;
	private LocalDateTime timestamp;
}
