package com.api.digital.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.digital.security.model.Device;
import com.api.digital.security.model.Vunerabilities;
import com.api.digital.security.repository.DeviceRepository;
import com.api.digital.security.repository.VunerabilitiesRepository;

@RestController
@RequestMapping("/vunerabilities")
public class VunerabilitiesController {
	
	@Autowired
	private VunerabilitiesRepository vunerabilitiesRepository;
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	//Rota para adicionar uma nova vulnerabilidade:
	@PostMapping
	public ResponseEntity<Vunerabilities> createVunerabilities(@RequestBody Vunerabilities vunerabilities){
		Long deviceId = vunerabilities.getDevice().getId();
		Device device = deviceRepository.findById(deviceId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lamento. Dispositivo não encontrado!"));
		
		vunerabilities.setDevice(device);
		
		Vunerabilities saveVunerabilities = vunerabilitiesRepository.save(vunerabilities);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saveVunerabilities);
		
	}
	
	//Rota para listar todas as vulnerabilidades
	@GetMapping
	public List<Vunerabilities> getAllVunerabilities(){
		return vunerabilitiesRepository.findAll();
	}
	
	//Rota para listar as vulnerabilidades de um dispositivo especifico
	@GetMapping("/devices/{deviceId}/vunerabilities")
	public List<Vunerabilities> getVunerabilitiesByDevice(@PathVariable Long deviceId){
		return vunerabilitiesRepository.findByDeviceId(deviceId);
	}
	
	//Rota para buscar vulnerabilidades pelo o ID
	@GetMapping("/{id}")
	public ResponseEntity<Vunerabilities> getVunerabilitiesById(@PathVariable Long id){
		return vunerabilitiesRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	//Rota para deletar uma vulnerabilidade
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVunerabilities(@PathVariable Long id){
		if(!vunerabilitiesRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		vunerabilitiesRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
