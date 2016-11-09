package de.nazaruk.services;

import de.nazaruk.persistence.UserEntity;

/**
 * Created by nazaruk on 11/5/16.
 */
public interface UserService {

    void save(UserEntity user);

    UserEntity findByUsername(String username);
}
