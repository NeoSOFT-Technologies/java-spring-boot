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

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.infrastructure.entity.Authority;
import com.springboot.rest.infrastructure.entity.SampleEntity2;

/**
 * Mapper for the entity {@link SampleEntity2} and its DTO called {@link SampleEntity2DTO}.
 *
 *With the hard-coded mappers, it could get very tedious in the future 
 *when we have lots of entities with many fields each
 *
 *So, here we are making use of ModelMapper to generate the DTO mappings automatically
 *
 */

@Component
public class SampleEntity2Mapper {
	
	// inject ModelMapper
	private ModelMapper modelMapper = new ModelMapper();
	
	////// DTO Mapping strategy //////////

	////////////////////////// 1. Using ModelMapper library /////////////////////
	// Entity to DTO Mapping
	public SampleEntity2DTO entityToDto(SampleEntity2 sampleEntity2) {
		return modelMapper.map(sampleEntity2, SampleEntity2DTO.class);
	}
	
    public List<SampleEntity2DTO> entitiesToDTOs(List<SampleEntity2> sampleEntity2s) {
        return sampleEntity2s.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
    }
	
	// DTO to entity Mapping
	public SampleEntity2 dtoToEntity(SampleEntity2DTO sampleEntity2DTO) {
		return modelMapper.map(sampleEntity2DTO, SampleEntity2.class);
	}
	
    public List<SampleEntity2> dtosToEntities(List<SampleEntity2DTO> sampleEntity2DTOs) {
        return sampleEntity2DTOs.stream().filter(Objects::nonNull).map(this::dtoToEntity).collect(Collectors.toList());
    }
	
	
	//////////////////////////2. Hard-coded way /////////////////////
    
	/*
	 * public List<SampleEntity2DTO>
	 * sampleEntitiesToSampleEntityDTOs(List<SampleEntity2> sampleEntities) { return
	 * sampleEntities.stream().filter(Objects::nonNull).map(this::
	 * sampleEntityToSampleEntityDTO).collect(Collectors.toList()); }
	 * 
	 * public SampleEntity2DTO sampleEntityToSampleEntityDTO(SampleEntity2
	 * sampleEntity) { return new SampleEntity2DTO(sampleEntity); }
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

    public SampleEntity2 sampleEntityFromId(Long id) {
        if (id == null) {
            return null;
        }
        SampleEntity2 sampleEntity2 = new SampleEntity2();
        sampleEntity2.setId(id);
        return sampleEntity2;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public SampleEntity2DTO toDtoId(SampleEntity2 user) {
        if (user == null) {
            return null;
        }
        SampleEntity2DTO userDto = new SampleEntity2DTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<SampleEntity2DTO> toDtoIdSet(Set<SampleEntity2> sampleEntity2s) {
        if (sampleEntity2s == null) {
            return Collections.emptySet();
        }

        Set<SampleEntity2DTO> sampleEntitySet = new HashSet<>();
        for (SampleEntity2 sampleEntityEntity : sampleEntity2s) {
            sampleEntitySet.add(this.toDtoId(sampleEntityEntity));
        }

        return sampleEntitySet;
    }

}
