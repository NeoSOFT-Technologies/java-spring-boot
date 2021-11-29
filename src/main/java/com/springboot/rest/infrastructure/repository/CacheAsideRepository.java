package com.springboot.rest.infrastructure.repository;

import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the A entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CacheAsideRepository extends JpaRepository<CacheAsideEntity, Long> {
	
	boolean deleteById(long id);
}
