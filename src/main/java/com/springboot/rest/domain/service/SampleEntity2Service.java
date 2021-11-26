package com.springboot.rest.domain.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.domain.port.spi.SampleEntity2PersistencePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.mapper.SampleEntity2Mapper;
import com.springboot.rest.rest.errors.BadRequestAlertException;

@Service
@Transactional
public class SampleEntity2Service implements SampleEntity2ServicePort {

    private static final String ENTITY_NAME = "a";

    @Autowired
    private RMapCache<Long, SampleEntity2> sampleRMapCache;
    private final SampleEntity2PersistencePort sampleEntity2PersistencePort;
    private final SampleEntity2Mapper sampleEntity2Mapper;

    public SampleEntity2Service(SampleEntity2PersistencePort sampleEntity2PersistencePort, SampleEntity2Mapper sampleEntity2Mapper) {
        this.sampleEntity2PersistencePort = sampleEntity2PersistencePort;
        this.sampleEntity2Mapper = sampleEntity2Mapper;
    }

    @Override
    public SampleEntity2 save(SampleEntity2DTO sampleEntity2DTO) {
    	//SampleEntity2 sampleEntity=sampleEntity2Mapper.dtoToEntity(sampleEntity2DTO);

//    	long random=(long) (Math.random()*(7000-4000+1)+4000);
//        this.sampleRMapCache.put(random, sampleEntity,60, TimeUnit.SECONDS);
//    	return sampleEntity;
        
        return sampleEntity2PersistencePort.save(sampleEntity2DTO);
    }

    @Override
    public SampleEntity2 update(Long id, SampleEntity2DTO sampleEntity2DTO) {

        if (sampleEntity2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleEntity2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleEntity2PersistencePort.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return sampleEntity2PersistencePort.save(sampleEntity2DTO);
    }

    @Override
    public boolean existsById(Long id) {
        return sampleEntity2PersistencePort.existsById(id);
    }

    @Override
    public List<SampleEntity2> findAll() {
        return sampleEntity2PersistencePort.findAll();
    }

    @Override
    public Optional<SampleEntity2> findById(Long id) {
    	return Optional.ofNullable(this.sampleRMapCache.get(id));
       // return sampleEntity2PersistencePort.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        //sampleEntity2PersistencePort.deleteById(id);
    	//this.sampleRMapCache.remove(id);
    	return sampleEntity2PersistencePort.deleteById(id);
    }

    @Override
    public Optional<SampleEntity2> patch(Long id, SampleEntity2DTO sampleEntity2DTO) {

        if (sampleEntity2DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleEntity2DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleEntity2PersistencePort.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return sampleEntity2PersistencePort
                .findById(sampleEntity2DTO.getId())
                .map(
                        existingA -> {
                            if (sampleEntity2DTO.getName() != null) {
                                existingA.setName(sampleEntity2DTO.getName());
                            }
                            if (sampleEntity2DTO.getPassword() != null) {
                                existingA.setPassword(sampleEntity2DTO.getPassword());
                            }
                            if (sampleEntity2DTO.getAge() != null) {
                                existingA.setAge(sampleEntity2DTO.getAge());
                            }
                            if (sampleEntity2DTO.getPhone() != null) {
                                existingA.setPhone(sampleEntity2DTO.getPhone());
                            }
                            return existingA;
                        }
                )
                .map(updatedA -> {
                	SampleEntity2DTO updatedSampleEntityDTO = sampleEntity2Mapper.entityToDto(updatedA);
                    sampleEntity2PersistencePort.save(updatedSampleEntityDTO);
                    return updatedA;
                });
    
    }

}