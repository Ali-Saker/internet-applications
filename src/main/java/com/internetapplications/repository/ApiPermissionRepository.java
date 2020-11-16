package com.internetapplications.repository;


import com.internetapplications.entity.ApiPermission;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiPermissionRepository extends BaseRepository<ApiPermission> {
    ApiPermission findByCode(String code);
}
