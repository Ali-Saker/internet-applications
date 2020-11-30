package com.internetapplications.repository;

import com.internetapplications.entity.Parameter;

public interface ParameterRepository extends BaseRepository<Parameter> {

    Parameter findTopByName(String name);
}
