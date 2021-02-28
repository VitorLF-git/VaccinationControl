package com.vitor.springcloudmysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitor.springcloudmysql.model.Vaccination;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
	
}