package com.springboot.rest.infrastructure.repository;

import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the A entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WriteThroughCacheEntityRepository extends JpaRepository<WriteThroughCacheEntity, Long> {
	
	
	 WriteThroughCacheEntity findById(long id);
	 boolean deleteById(long id);
}
