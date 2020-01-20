package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.User;

public interface UserRepository extends CrudRepository<User, String> {
}
