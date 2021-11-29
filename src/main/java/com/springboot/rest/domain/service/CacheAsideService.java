package com.springboot.rest.domain.service;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import com.springboot.rest.domain.port.spi.CacheAsidePersistencePort;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.mapper.CacheAsideMapper;
import com.springboot.rest.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CacheAsideService implements CacheAsideServicePort {

    private static final String ENTITY_NAME = "a";

    private final CacheAsidePersistencePort sampleEntityPersistencePort;
    private final CacheAsideMapper sampleEntityMapper;

    public CacheAsideService(CacheAsidePersistencePort sampleEntityPersistencePort, CacheAsideMapper sampleEntityMapper) {
        this.sampleEntityPersistencePort = sampleEntityPersistencePort;
        this.sampleEntityMapper = sampleEntityMapper;
    }

    @Override
    public CacheAsideEntity save(CacheAsideDTO sampleEntityDTO) {
        return sampleEntityPersistencePort.save(sampleEntityDTO);
    }

    @Override
    public CacheAsideEntity update(Long id, CacheAsideDTO sampleEntityDTO) {

        if (sampleEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleEntityPersistencePort.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return sampleEntityPersistencePort.save(sampleEntityDTO);
    }

    @Override
    public boolean existsById(Long id) {
        return sampleEntityPersistencePort.existsById(id);
    }

    @Override
    public List<CacheAsideEntity> findAll() {
        return sampleEntityPersistencePort.findAll();
    }

    @Override
    public Optional<CacheAsideEntity> findById(Long id) {
        return sampleEntityPersistencePort.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return sampleEntityPersistencePort.deleteById(id);
    }

    @Override
    public Optional<CacheAsideEntity> patch(Long id, CacheAsideDTO sampleEntityDTO) {

        if (sampleEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleEntityPersistencePort.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return sampleEntityPersistencePort
                .findById(sampleEntityDTO.getId())
                .map(
                        existingA -> {
                            if (sampleEntityDTO.getName() != null) {
                                existingA.setName(sampleEntityDTO.getName());
                            }
                            if (sampleEntityDTO.getPassword() != null) {
                                existingA.setPassword(sampleEntityDTO.getPassword());
                            }
                            if (sampleEntityDTO.getAge() != null) {
                                existingA.setAge(sampleEntityDTO.getAge());
                            }
                            if (sampleEntityDTO.getPhone() != null) {
                                existingA.setPhone(sampleEntityDTO.getPhone());
                            }
                            return existingA;
                        }
                )
                .map(updatedA -> {
                	CacheAsideDTO updatedSampleEntityDTO = sampleEntityMapper.entityToDto(updatedA);
                    sampleEntityPersistencePort.save(updatedSampleEntityDTO);
                    return updatedA;
                });
    
    }

}