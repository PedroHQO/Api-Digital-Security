package com.api.digital.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.digital.security.authenticate.JWTTokenProvider;
import com.api.digital.security.authenticate.JwtAuthenticationResponse;
import com.api.digital.security.dto.DeviceDataTransferObject;
import com.api.digital.security.exceptions.DeviceDependencyException;
import com.api.digital.security.exceptions.IdNotFoudException;
import com.api.digital.security.model.Device;
import com.api.digital.security.model.LoginRequest;
import com.api.digital.security.repository.DeviceRepository;
import com.api.digital.security.service.DeviceMapper;
import com.api.digital.security.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	DeviceDataTransferObject dataTransferObject;

	@Autowired
	JWTTokenProvider jwtTokenProvider;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private final DeviceService deviceService;

	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	// O método retorna uma lista (List<Device>) de dispositivos que correspondem ao
	// nome informado.
	public List<Device> getDevicesByName(String name) {
		return deviceRepository.findByName(name);
	}

	@PostMapping("/login")
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		String token = jwtTokenProvider.generateToken(loginRequest.getUsername());
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@PostMapping("Criar")
	public ResponseEntity<DeviceDataTransferObject> createDevice(
			@RequestBody DeviceDataTransferObject deviceDataTransferObject) {
		Device device = DeviceMapper.convertToEntity(deviceDataTransferObject);
		Device savedDevice = deviceRepository.save(device);
		DeviceDataTransferObject savedDataTransferObject = DeviceMapper.convertToDTO(savedDevice);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDataTransferObject);
	}

	// Listar todos os dispositivos
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@GetMapping("Listar")
	public ResponseEntity<List<DeviceDataTransferObject>> getAllDevices() {
		List<Device> devices = deviceRepository.findAll();
		List<DeviceDataTransferObject> deviceDataTransferObjects = devices.stream().map(DeviceMapper::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(deviceDataTransferObjects);
	}

	// Rotas para buscar um dispositivo pelo ID
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@GetMapping("Buscar/{id}")
	public ResponseEntity<DeviceDataTransferObject> getDeviceById(@PathVariable Long id) {

		DeviceDataTransferObject deviceDataTransferObject = deviceService.findDeviceById(id);
		return ResponseEntity.ok(deviceDataTransferObject);
	}

	// rota para atualizar um dispositivo
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("Update/{id}")
	public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails) {
		Device device = deviceService.findDeviceById(id).toEntity();

		device.setName(deviceDetails.getName());
		device.setIpAddress(deviceDetails.getIpAddress());
		device.setLocation(deviceDetails.getLocation());

		Device updateDevice = deviceRepository.save(device);
		return ResponseEntity.ok(updateDevice);
	}

	// Para deletar um disposito baseado no seu id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
		
		DeviceDataTransferObject device = deviceService.findDeviceById(id);
		
		if(device.getVulnerabilities() != null && !device.getVulnerabilities().isEmpty()) {
			throw new DeviceDependencyException("Erro!\n O Dispositivo possui vulnerabilidade associadas e não pode ser excluído.");
		}

		deviceRepository.deleteById(id);// Exclui o dispositivo se tiver o id
		return ResponseEntity.noContent().build();// Retorno 204 No content
	}

}
