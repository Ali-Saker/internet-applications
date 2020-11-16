package com.internetapplications.repository;

import com.internetapplications.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@NoRepositoryBean
@RepositoryRestResource(
        exported = false
)
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    T findByUuid(String var1);
}
