package com.multitenancy.dao;

import org.springframework.stereotype.Repository;

import com.multitenancy.entity.CustomerEntity;
import com.multitenancy.persistence.BaseDao;

@Repository
public class CustomerDao extends BaseDao<CustomerEntity, Long> {
    public static final String EMAIL = "email";

    public CustomerDao() {
        super(CustomerEntity.class);
    }
}
