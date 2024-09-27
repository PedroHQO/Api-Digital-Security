package com.api.digital.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.digital.security.model.Vunerabilities;

public interface VunerabilitiesRepository extends JpaRepository<Vunerabilities, Long> {
	List<Vunerabilities> findByName(String name);

}
