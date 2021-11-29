package com.springboot.rest.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
//import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.infrastructure.entity.Authority;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;

/**
 * Mapper for the entity {@link CacheAsideEntity} and its DTO called {@link CacheAsideDTO}.
 *
 *With the hard-coded mappers, it could get very tedious in the future 
 *when we have lots of entities with many fields each
 *
 *So, here we are making use of ModelMapper to generate the DTO mappings automatically
 *
 */

@Component
public class CacheAsideMapper {
	
	// inject ModelMapper
	private ModelMapper modelMapper = new ModelMapper();
	
	////// DTO Mapping strategy //////////

	////////////////////////// 1. Using ModelMapper library /////////////////////
	// Entity to DTO Mapping
	public CacheAsideDTO entityToDto(CacheAsideEntity sampleEntity) {
		return modelMapper.map(sampleEntity, CacheAsideDTO.class);
	}
	
    public List<CacheAsideDTO> entitiesToDTOs(List<CacheAsideEntity> sampleEntities) {
        return sampleEntities.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
    }
	
	// DTO to entity Mapping
	public CacheAsideEntity dtoToEntity(CacheAsideDTO sampleEntityDTO) {
		return modelMapper.map(sampleEntityDTO, CacheAsideEntity.class);
	}
	
    public List<CacheAsideEntity> dtosToEntities(List<CacheAsideDTO> sampleEntityDTOs) {
        return sampleEntityDTOs.stream().filter(Objects::nonNull).map(this::dtoToEntity).collect(Collectors.toList());
    }
	
	
	//////////////////////////2. Hard-coded way /////////////////////
    
	/*
	 * public List<SampleEntityDTO>
	 * sampleEntitiesToSampleEntityDTOs(List<SampleEntity> sampleEntities) { return
	 * sampleEntities.stream().filter(Objects::nonNull).map(this::
	 * sampleEntityToSampleEntityDTO).collect(Collectors.toList()); }
	 * 
	 * public SampleEntityDTO sampleEntityToSampleEntityDTO(SampleEntity
	 * sampleEntity) { return new SampleEntityDTO(sampleEntity); }
	 * 
	 * public List<User> sampleEntityDTOsToSampleEntities(List<AdminUserDTO>
	 * userDTOs) { return userDTOs.stream().filter(Objects::nonNull).map(this::
	 * sampleEntityDTOToSampleEntity).collect(Collectors.toList()); }
	 * 
	 * public User sampleEntityDTOToSampleEntity(AdminUserDTO userDTO) { if (userDTO
	 * == null) { return null; } else { User user = new User();
	 * user.setId(userDTO.getId()); user.setLogin(userDTO.getLogin());
	 * user.setFirstName(userDTO.getFirstName());
	 * user.setLastName(userDTO.getLastName()); user.setEmail(userDTO.getEmail());
	 * user.setImageUrl(userDTO.getImageUrl());
	 * user.setActivated(userDTO.isActivated());
	 * user.setLangKey(userDTO.getLangKey()); Set<Authority> authorities =
	 * this.authoritiesFromStrings(userDTO.getAuthorities());
	 * user.setAuthorities(authorities); return user; } }
	 */
    

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(
                        string -> {
                            Authority auth = new Authority();
                            auth.setName(string);
                            return auth;
                        }
                    )
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

    public CacheAsideEntity sampleEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        CacheAsideEntity sampleEntity = new CacheAsideEntity();
        sampleEntity.setId(id);
        return sampleEntity;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public CacheAsideDTO toDtoId(CacheAsideEntity user) {
        if (user == null) {
            return null;
        }
        CacheAsideDTO userDto = new CacheAsideDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<CacheAsideDTO> toDtoIdSet(Set<CacheAsideEntity> sampleEntities) {
        if (sampleEntities == null) {
            return Collections.emptySet();
        }

        Set<CacheAsideDTO> sampleEntitySet = new HashSet<>();
        for (CacheAsideEntity sampleEntityEntity : sampleEntities) {
            sampleEntitySet.add(this.toDtoId(sampleEntityEntity));
        }

        return sampleEntitySet;
    }

}
