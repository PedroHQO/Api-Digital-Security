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

import com.api.digital.security.authenticate.JWTTokenProvider;
import com.api.digital.security.authenticate.JwtAuthenticationResponse;
import com.api.digital.security.model.Device;
import com.api.digital.security.model.LoginRequest;
import com.api.digital.security.repository.DeviceRepository;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private DeviceRepository deviceRepository;
	//O método retorna uma lista (List<Device>) de dispositivos que correspondem ao nome informado.
	public List<Device> getDevicesByName(String name){
		return deviceRepository.findByName(name);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		String token = jwtTokenProvider.generateToken(loginRequest.getUsername());
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	
	@PostMapping
	public ResponseEntity<Device> createDevice(@RequestBody Device device){
		Device savDevice = deviceRepository.save(device);
		return ResponseEntity.status(HttpStatus.CREATED).body(savDevice);
	}
	
	//Listar todos os dispositivos
	@GetMapping
	public ResponseEntity<List<Device>> getAllDevices(){
		List<Device> devices = deviceRepository.findAll();
		return ResponseEntity.ok(devices);
	}
	
	//Rotas para buscar um dispositivo pelo ID
	@GetMapping("/{id}")
	public ResponseEntity<Device> getDeviceById(@PathVariable Long id){
		return deviceRepository.findById(id)
				.map(ResponseEntity::ok)//200 ok
				.orElse(ResponseEntity.notFound().build());//404 Not Found
		
	}
	
	//rota para atualizar um dispositivo
		@PutMapping("/{id}")
		public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody Device deviceDetails){	
			return deviceRepository.findById(id)
					.map(device -> {
						device.setName(deviceDetails.getName());
						device.setIpAddress(deviceDetails.getIpAddress());
						device.setLocation(deviceDetails.getLocation());
						Device updateDevice = deviceRepository.save(device);
						return ResponseEntity.ok(updateDevice);
					}).orElse(ResponseEntity.notFound().build());
			
		}
		
		//Para deletar um disposito baseado no seu id
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteDevice(@PathVariable Long id){
			if(!deviceRepository.existsById(id)){
				return ResponseEntity.notFound().build(); //retorna 404 se não encontrar o id
			}
			
			deviceRepository.deleteById(id);//Exclui o dispositivo se tiver o id
			return ResponseEntity.noContent().build();//Retorno 204 No content
		}
	
	
}
