package com.api.digital.security.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.api.digital.security.dto.DeviceDataTransferObject;
import com.api.digital.security.dto.VulnerabilityDataTransferObject;
import com.api.digital.security.model.Device;

import jakarta.validation.constraints.AssertFalse.List;

@Component
public class DeviceMapper {

	public static DeviceDataTransferObject convertToDTO(Device device) {
		DeviceDataTransferObject deviceDataTransferObject = new DeviceDataTransferObject();
		
		deviceDataTransferObject.setId(device.getId());
		deviceDataTransferObject.setName(device.getName());
		deviceDataTransferObject.setIpAddress(device.getIpAddress());
		deviceDataTransferObject.setLocation(device.getLocation());
		
		
		deviceDataTransferObject.setVulnerabilities(
			device.getVulnerabilities().stream()
				.map(VulnerabilityMapper::convertToDTO)
				.collect(Collectors.toList())
			);
		
		return deviceDataTransferObject;
	
	}
	
	public static Device convertToEntity(DeviceDataTransferObject deviceDataTransferObject) {
		Device device = new Device();
		device.setId(deviceDataTransferObject.getId());
		device.setIpAddress(deviceDataTransferObject.getIpAddress());
		device.setLocation(deviceDataTransferObject.getLocation());
		device.setName(deviceDataTransferObject.getName());
		return device;
	}
}
