package com.api.digital.security.service;

import org.springframework.stereotype.Service;

import com.api.digital.security.dto.DeviceDataTransferObject;
import com.api.digital.security.exceptions.IdNotFoudException;
import com.api.digital.security.model.Device;
import com.api.digital.security.repository.DeviceRepository;

@Service
public class DeviceService {
	
	private final DeviceRepository deviceRepository;
	private final DeviceMapper deviceMapper;
	
	public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
		this.deviceRepository = deviceRepository;
		this.deviceMapper = deviceMapper;
	}
	
	public DeviceDataTransferObject findDeviceById(Long id){
		
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new IdNotFoudException("Dispositivo com ID: " + id + " não encontrado."));
		
		return deviceMapper.convertToDTO(device);
	}
	
	
}
