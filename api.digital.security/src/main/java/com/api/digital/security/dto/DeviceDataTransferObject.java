package com.api.digital.security.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.api.digital.security.model.Device;


@Component
public class DeviceDataTransferObject {
	
	private Long id;
	private String name;
	private String ipAddress;
	private String location;
	private List<VulnerabilityDataTransferObject> vulnerabilities;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<VulnerabilityDataTransferObject> getVulnerabilities() {
		return vulnerabilities;
	}
	public void setVulnerabilities(List<VulnerabilityDataTransferObject> vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}
	
	public Device toEntity() {
		Device device = new Device();
		device.setId(this.getId());
		device.setIpAddress(this.getIpAddress());
		device.setLocation(this.getLocation());
		device.setName(this.getName());
		
		return device;
	}
	
	
}
