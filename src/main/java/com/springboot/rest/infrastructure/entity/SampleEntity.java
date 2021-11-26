package com.springboot.rest.infrastructure.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



/**
 * A A.
 */
@Entity
@Data
@Table(name = "WriteThroughCache")

//
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SampleEntity implements Serializable {

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


    public SampleEntity name(String name) {
        this.name = name;
        return this;
    }


    public SampleEntity password(String password) {
        this.password = password;
        return this;
    }


    public SampleEntity age(Integer age) {
        this.age = age;
        return this;
    }


    public SampleEntity phone(Integer phone) {
        this.phone = phone;
        return this;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SampleEntity)) {
            return false;
        }
        return id != null && id.equals(((SampleEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

}
