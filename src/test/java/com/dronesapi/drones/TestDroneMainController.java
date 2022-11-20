package com.dronesapi.drones;

import com.dronesapi.drones.controller.DroneController;
import com.dronesapi.drones.exception.BadRequestException;
import com.dronesapi.drones.model.request.DroneRegisterRequest;
import com.dronesapi.drones.model.response.DroneResponse;
import com.dronesapi.drones.service.DroneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestDroneMainController {

	@InjectMocks
	DroneController droneController;
	@Mock
	DroneService droneService;

	@Test
	public void testRegisterDrone() throws BadRequestException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		DroneResponse registerDroneResponse = new DroneResponse();
		registerDroneResponse.setMessage("success");
		registerDroneResponse.setSerialNumber("KY12345");
		registerDroneResponse.setMessage("New Drone created successfully");
		registerDroneResponse.setTimestamp(LocalDateTime.now());
		when(droneService.register(any(DroneRegisterRequest.class))).thenReturn(registerDroneResponse);
		DroneRegisterRequest drone = new DroneRegisterRequest("KY12345", "Lightweight", 500.0, new BigDecimal("0.95"),
				"IDLE");
		ResponseEntity<DroneResponse> responseEntity = droneController.registerDrone(drone);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(registerDroneResponse);

	}
}
