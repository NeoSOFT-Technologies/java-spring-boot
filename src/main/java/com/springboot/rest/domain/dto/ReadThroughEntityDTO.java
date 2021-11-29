package com.springboot.rest.domain.dto;


import com.springboot.rest.infrastructure.entity.ReadThroughEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ReadThroughEntityDTO {

    private Long id;

    private String name;

    private String password;

    private Integer age;

    private Integer phone;
    

 

    public ReadThroughEntityDTO(ReadThroughEntity readThroughEntity) {
        this.id = readThroughEntity.getId();
        this.name = readThroughEntity.getName();
        this.password = readThroughEntity.getPassword();
        this.age = readThroughEntity.getAge();
        this.phone = readThroughEntity.getPhone();
    }

}
