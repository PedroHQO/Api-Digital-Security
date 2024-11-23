package com.api.digital.security.dto;

import java.util.List;



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
	
	
}
