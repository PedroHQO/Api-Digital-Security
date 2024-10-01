package com.api.digital.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.digital.security.model.Device;


public interface DeviceRepository extends JpaRepository<Device, Long> {
	List<Device> findByName(String name);
}
