package de.nazaruk.persistence;

import javax.persistence.PrePersist;

/**
 * Created by nazaruk on 11/9/16.
 */
public class TrimEntityListener {

    @PrePersist
    public void trim(UserEntity userEntity) {
        userEntity.setUsername(userEntity.getUsername().trim());
    }
}
