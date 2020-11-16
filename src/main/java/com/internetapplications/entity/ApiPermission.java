package com.internetapplications.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ApiPermission extends BaseEntity {

    @Column(
            nullable = false,
            unique = true
    )
    private String code;

    @Column(
            nullable = false
    )
    private String name;

    private String description;

    private String link;

    public ApiPermission() {

    }
    public ApiPermission(String code, String name, String description, String link) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
