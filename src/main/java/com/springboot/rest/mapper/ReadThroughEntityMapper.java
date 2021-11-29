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

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.infrastructure.entity.Authority;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;

/**
 * Mapper for the entity {@link ReadThroughEntity} and its DTO called {@link ReadThroughEntityDTO}.
 *
 *With the hard-coded mappers, it could get very tedious in the future 
 *when we have lots of entities with many fields each
 *
 *So, here we are making use of ModelMapper to generate the DTO mappings automatically
 *
 */

@Component
public class ReadThroughEntityMapper {
	
	// inject ModelMapper
	private ModelMapper modelMapper = new ModelMapper();
	
	////// DTO Mapping strategy //////////

	////////////////////////// 1. Using ModelMapper library /////////////////////
	// Entity to DTO Mapping
	public ReadThroughEntityDTO entityToDto(ReadThroughEntity readThroughEntity) {
		return modelMapper.map(readThroughEntity, ReadThroughEntityDTO.class);
	}
	
    public List<ReadThroughEntityDTO> entitiesToDTOs(List<ReadThroughEntity> readThroughEntities) {
        return readThroughEntities.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
    }
	
	// DTO to entity Mapping
	public ReadThroughEntity dtoToEntity(ReadThroughEntityDTO readThroughEntityDTO) {
		return modelMapper.map(readThroughEntityDTO, ReadThroughEntity.class);
	}
	
    public List<ReadThroughEntity> dtosToEntities(List<ReadThroughEntityDTO> readThroughEntityDTOs) {
        return readThroughEntityDTOs.stream().filter(Objects::nonNull).map(this::dtoToEntity).collect(Collectors.toList());
    }
	
	
	//////////////////////////2. Hard-coded way /////////////////////
    
	/*
	 * public List<ReadThroughEntityDTO>
	 * sampleEntitiesToSampleEntityDTOs(List<ReadThroughEntity> sampleEntities) { return
	 * sampleEntities.stream().filter(Objects::nonNull).map(this::
	 * sampleEntityToSampleEntityDTO).collect(Collectors.toList()); }
	 * 
	 * public ReadThroughEntityDTO sampleEntityToSampleEntityDTO(ReadThroughEntity
	 * sampleEntity) { return new ReadThroughEntityDTO(sampleEntity); }
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

    public ReadThroughEntity sampleEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        ReadThroughEntity readThroughEntity = new ReadThroughEntity();
        readThroughEntity.setId(id);
        return readThroughEntity;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public ReadThroughEntityDTO toDtoId(ReadThroughEntity user) {
        if (user == null) {
            return null;
        }
        ReadThroughEntityDTO userDto = new ReadThroughEntityDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<ReadThroughEntityDTO> toDtoIdSet(Set<ReadThroughEntity> readThroughEntities) {
        if (readThroughEntities == null) {
            return Collections.emptySet();
        }

        Set<ReadThroughEntityDTO> sampleEntitySet = new HashSet<>();
        for (ReadThroughEntity sampleEntityEntity : readThroughEntities) {
            sampleEntitySet.add(this.toDtoId(sampleEntityEntity));
        }

        return sampleEntitySet;
    }

}
