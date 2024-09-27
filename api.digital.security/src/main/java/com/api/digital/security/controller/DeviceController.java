package com.api.digital.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.digital.security.model.Device;
import com.api.digital.security.repository.DeviceRepository;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	private DeviceRepository deviceRepository;
	//O método retorna uma lista (List<Device>) de dispositivos que correspondem ao nome informado.
	public List<Device> getDevicesByName(String name){
		return deviceRepository.findByName(name);
	}
	
	@PostMapping
	public ResponseEntity<Device> createDevice(@RequestBody Device device){
		Device savDevice = deviceRepository.save(device);
		return ResponseEntity.status(HttpStatus.CREATED).body(savDevice);
	}
	
	//rotas para buscar um dispositivo pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<Device> getDeviceById(@PathVariable Long id){
		return deviceRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	//rota para atualizar um dispositivo
		@PutMapping("/{id}")
		public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails){	
			return deviceRepository.findById(id)
					.map(device -> {
						device.setName(deviceDetails.getName());
						device.setIpAdress(deviceDetails.getIpAdress());
						device.setLocation(deviceDetails.getLocation());
						Device updateDevice = deviceRepository.save(device);
						return ResponseEntity.ok(updateDevice);
					}).orElse(ResponseEntity.notFound().build());
			
		}
		
		//para deletar um disposito
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteDevice(@PathVariable Long id){
			return deviceRepository.findById(id)
					.map(device -> {
						deviceRepository.delete(device);
						return ResponseEntity.noContent().build();
					}).orElse(ResponseEntity.notFound().build());
		}
	
	
}