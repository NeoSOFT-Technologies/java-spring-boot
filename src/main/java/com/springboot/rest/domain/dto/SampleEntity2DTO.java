package com.springboot.rest.domain.dto;


import com.springboot.rest.infrastructure.entity.SampleEntity2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SampleEntity2DTO {

    private Long id;

    private String name;

    private String password;

    private Integer age;

    private Integer phone;
    

 

    public SampleEntity2DTO(SampleEntity2 sampleEntity2) {
        this.id = sampleEntity2.getId();
        this.name = sampleEntity2.getName();
        this.password = sampleEntity2.getPassword();
        this.age = sampleEntity2.getAge();
        this.phone = sampleEntity2.getPhone();
    }

}
