package com.internetapplications.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Parameter extends BaseEntity {

    @Column(
            nullable = false
    )
    private String name;

    @Column(
            nullable = false
    )
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
