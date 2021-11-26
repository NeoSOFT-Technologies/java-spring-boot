package com.springboot.rest.domain.dto;


import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class WriteThroughCacheEntityDTO {

    private Long id;

    private String name;

    private String password;

    private Integer age;

    private Integer phone;
    

 

    public WriteThroughCacheEntityDTO(WriteThroughCacheEntity sampleEntity) {
        this.id = sampleEntity.getId();
        this.name = sampleEntity.getName();
        this.password = sampleEntity.getPassword();
        this.age = sampleEntity.getAge();
        this.phone = sampleEntity.getPhone();
    }

}
