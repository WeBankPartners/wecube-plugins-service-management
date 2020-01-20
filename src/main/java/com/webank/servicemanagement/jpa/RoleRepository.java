package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.Role;

public interface RoleRepository extends CrudRepository<Role, String> {
}
