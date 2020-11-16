package com.internetapplications.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role extends BaseEntity {
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

    @ManyToMany
    private Set<ApiPermission> apiPermissions = new HashSet<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<ApiPermission> getApiPermissions() {
        return apiPermissions;
    }

    public void setApiPermissions(Set<ApiPermission> apiPermissions) {
        this.apiPermissions = apiPermissions;
    }
}
