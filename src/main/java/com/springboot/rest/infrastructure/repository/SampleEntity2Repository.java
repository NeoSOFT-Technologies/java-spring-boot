package com.springboot.rest.infrastructure.repository;

import com.springboot.rest.infrastructure.entity.SampleEntity2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the A entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SampleEntity2Repository extends JpaRepository<SampleEntity2, Long> {
	
	SampleEntity2 findById(long id);
	boolean deleteById(long id);
}
