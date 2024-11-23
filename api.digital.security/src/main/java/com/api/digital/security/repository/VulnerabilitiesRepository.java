package com.api.digital.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.digital.security.model.Vulnerabilities;

public interface VulnerabilitiesRepository extends JpaRepository<Vulnerabilities, Long> {
	List<Vulnerabilities> findByName(String name);
	List<Vulnerabilities> findByDeviceId(Long deviceId);

}
