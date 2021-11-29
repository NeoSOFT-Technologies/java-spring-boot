package com.springboot.rest.domain.dto;


import com.springboot.rest.infrastructure.entity.CacheAsideEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CacheAsideDTO {

    private Long id;

    private String name;

    private String password;

    private Integer age;

    private Integer phone;
    

 

    public CacheAsideDTO(CacheAsideEntity sampleEntity) {
        this.id = sampleEntity.getId();
        this.name = sampleEntity.getName();
        this.password = sampleEntity.getPassword();
        this.age = sampleEntity.getAge();
        this.phone = sampleEntity.getPhone();
    }

}
