package com.api.digital.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.digital.security.model.Device;


public interface DeviceRepository extends JpaRepository<Device, Long> {
	List<Device> findByName(String name);
	
	@Query("SELECT d FROM Device d LEFT JOIN FETCH d.vulnerabilities WHERE d.id = :id")
	Optional<Device> findByWithVulnerabilities(@Param("id") Long id);
}
