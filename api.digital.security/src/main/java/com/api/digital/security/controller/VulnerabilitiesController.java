package com.api.digital.security.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.digital.security.dto.DeviceDataTransferObject;
import com.api.digital.security.dto.VulnerabilityDataTransferObject;
import com.api.digital.security.model.Device;
import com.api.digital.security.model.Vulnerabilities;
import com.api.digital.security.repository.DeviceRepository;
import com.api.digital.security.repository.VulnerabilitiesRepository;
import com.api.digital.security.service.VulnerabilityMapper;
import com.api.digital.security.service.VulnerabilityService;

@RestController
@RequestMapping("/vulnerabilities")
public class VulnerabilitiesController {
	
	@Autowired
	private VulnerabilitiesRepository vulnerabilitiesRepository;
	
	@Autowired
	private final VulnerabilityService vulnerabilityService;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	public VulnerabilitiesController(VulnerabilityService vulnerabilityService) {
		this.vulnerabilityService = vulnerabilityService;
	}

	//Rota para adicionar uma nova vulnerabilidade:
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@PostMapping
	public ResponseEntity<VulnerabilityDataTransferObject> createVulnerabilities(@RequestBody Vulnerabilities vulnerabilities) {
		Long deviceId = vulnerabilities.getDevice().getId();
		Device device = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lamento. Dispositivo não encontrado!"));
		
		vulnerabilities.setDevice(device);
		vulnerabilities.setDetectedAt(LocalDateTime.now());

		Vulnerabilities savedVulnerabilities = vulnerabilitiesRepository.save(vulnerabilities);
		VulnerabilityDataTransferObject vulnerabilityDataTransferObject = VulnerabilityMapper.convertToDTO(savedVulnerabilities);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vulnerabilityDataTransferObject);
	}
	
	//Rota para listar todas as vulnerabilidades
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@GetMapping
	public ResponseEntity<List<VulnerabilityDataTransferObject>> getAllVulnerabilities(){
		List<Vulnerabilities> vulnerabilities = vulnerabilitiesRepository.findAll();
		List<VulnerabilityDataTransferObject> vulnerabilityDataTransferObjects = vulnerabilities.stream()
				.map(VulnerabilityMapper::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(vulnerabilityDataTransferObjects);
	}
	
	//Rota para listar as vulnerabilidades de um dispositivo especifico
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@GetMapping("/device/{deviceId}/vulnerabilities")
	public ResponseEntity<List<VulnerabilityDataTransferObject>> getVulnerabilitiesByDevice(@PathVariable Long deviceId) {
		List<Vulnerabilities> vulnerabilities = vulnerabilitiesRepository.findByDeviceId(deviceId);
		List<VulnerabilityDataTransferObject> vulnerabilityDataTransferObject = vulnerabilities.stream()
				.map(VulnerabilityMapper::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(vulnerabilityDataTransferObject);
	}
	
	// Rota para listar uma vulnerabilidade específica por ID
	@PreAuthorize("hasAnyRole('ADMIN', 'COMMON_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<VulnerabilityDataTransferObject> getVulnerabilityById(@PathVariable Long id) {
		
		VulnerabilityDataTransferObject vulnerabilityDataTransferObject = vulnerabilityService.findByVulnerabilityId(id);
		return ResponseEntity.ok(vulnerabilityDataTransferObject);	
	}

	
	//Rota para deletar uma vulnerabilidade
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVulnerabilities(@PathVariable Long id){
		if(!vulnerabilitiesRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		vulnerabilitiesRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}