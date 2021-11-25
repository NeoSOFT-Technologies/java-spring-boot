package com.springboot.rest.infrastructure.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;

/**
 * A A.
 */
@Entity
@Data
@Table(name = "a")

//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampleEntity2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone")
    private Integer phone;


    public SampleEntity2 name(String name) {
        this.name = name;
        return this;
    }


    public SampleEntity2 password(String password) {
        this.password = password;
        return this;
    }


    public SampleEntity2 age(Integer age) {
        this.age = age;
        return this;
    }


    public SampleEntity2 phone(Integer phone) {
        this.phone = phone;
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SampleEntity2)) {
            return false;
        }
        return id != null && id.equals(((SampleEntity2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

}
