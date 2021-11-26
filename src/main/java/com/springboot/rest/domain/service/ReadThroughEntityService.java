package com.springboot.rest.domain.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.domain.port.spi.ReadThroughEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import com.springboot.rest.mapper.ReadThroughEntityMapper;
import com.springboot.rest.rest.errors.BadRequestAlertException;

@Service
@Transactional
public class ReadThroughEntityService implements SampleEntity2ServicePort {

    private static final String ENTITY_NAME = "a";

    @Autowired
    private RMapCache<Long, ReadThroughEntity> sampleRMapCache;
    private final ReadThroughEntityPersistencePort readThroughEntityPersistencePort;
    private final ReadThroughEntityMapper readThroughEntityMapper;

    public ReadThroughEntityService(ReadThroughEntityPersistencePort readThroughEntityPersistencePort, ReadThroughEntityMapper readThroughEntityMapper) {
        this.readThroughEntityPersistencePort = readThroughEntityPersistencePort;
        this.readThroughEntityMapper = readThroughEntityMapper;
    }

    @Override
    public ReadThroughEntity save(ReadThroughEntityDTO readThroughEntityDTO) {
    	//ReadThroughEntity sampleEntity=readThroughEntityMapper.dtoToEntity(sampleEntity2DTO);

//    	long random=(long) (Math.random()*(7000-4000+1)+4000);
//        this.sampleRMapCache.put(random, sampleEntity,60, TimeUnit.SECONDS);
//    	return sampleEntity;
        
        return readThroughEntityPersistencePort.save(readThroughEntityDTO);
    }

    @Override
    public ReadThroughEntity update(Long id, ReadThroughEntityDTO readThroughEntityDTO) {

        if (readThroughEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, readThroughEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!readThroughEntityPersistencePort.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return readThroughEntityPersistencePort.save(readThroughEntityDTO);
    }

    @Override
    public boolean existsById(Long id) {
        return readThroughEntityPersistencePort.existsById(id);
    }

    @Override
    public List<ReadThroughEntity> findAll() {
        return readThroughEntityPersistencePort.findAll();
    }

    @Override
    public Optional<ReadThroughEntity> findById(Long id) {
    	return Optional.ofNullable(this.sampleRMapCache.get(id));
       // return readThroughEntityPersistencePort.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        //readThroughEntityPersistencePort.deleteById(id);
    	//this.sampleRMapCache.remove(id);
    	return readThroughEntityPersistencePort.deleteById(id);
    }

    @Override
    public Optional<ReadThroughEntity> patch(Long id, ReadThroughEntityDTO readThroughEntityDTO) {

        if (readThroughEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, readThroughEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!readThroughEntityPersistencePort.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        return readThroughEntityPersistencePort
                .findById(readThroughEntityDTO.getId())
                .map(
                        existingA -> {
                            if (readThroughEntityDTO.getName() != null) {
                                existingA.setName(readThroughEntityDTO.getName());
                            }
                            if (readThroughEntityDTO.getPassword() != null) {
                                existingA.setPassword(readThroughEntityDTO.getPassword());
                            }
                            if (readThroughEntityDTO.getAge() != null) {
                                existingA.setAge(readThroughEntityDTO.getAge());
                            }
                            if (readThroughEntityDTO.getPhone() != null) {
                                existingA.setPhone(readThroughEntityDTO.getPhone());
                            }
                            return existingA;
                        }
                )
                .map(updatedA -> {
                	ReadThroughEntityDTO updatedSampleEntityDTO = readThroughEntityMapper.entityToDto(updatedA);
                    readThroughEntityPersistencePort.save(updatedSampleEntityDTO);
                    return updatedA;
                });
    
    }

}