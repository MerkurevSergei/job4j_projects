package com.github.merkurevsergei.repository;

import com.github.merkurevsergei.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
