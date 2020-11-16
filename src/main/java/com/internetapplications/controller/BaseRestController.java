package com.internetapplications.controller;

import com.internetapplications.entity.BaseEntity;
import com.internetapplications.repository.BaseRepository;

abstract class BaseRestController<T extends BaseEntity> {

    public abstract BaseRepository<T> repository();
}
