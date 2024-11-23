package com.api.digital.security.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String ipAddress;
	private String location;
	
	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Vulnerabilities> vulnerabilities = new ArrayList<>();
	
	
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
	public List<Vulnerabilities> getVulnerabilities() {
		return vulnerabilities;
	}
	public void setVulnerabilities(List<Vulnerabilities> vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}
	
}
