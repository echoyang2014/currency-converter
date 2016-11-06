package de.nazaruk.services;

import de.nazaruk.entity.UserEntity;

import java.util.List;

/**
 * Created by nazaruk on 11/5/16.
 */
public interface UserService {

    List<UserEntity> findAllUsers();

    void save(UserEntity user);

    UserEntity findByUsername(String username);
}
