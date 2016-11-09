package de.nazaruk.persistance;

import de.nazaruk.persistence.UserEntity;
import de.nazaruk.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by nazaruk on 11/8/16.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAndFindUser() {
        UserEntity entity = createAndSaveUserEntity();

        assertNotNull(entity.getId());
        assertEquals(USERNAME, entity.getUsername());
        assertEquals(PASSWORD, entity.getPassword());

        assertEquals(entity.getId(), userRepository.findByUsername(USERNAME).getId());
    }

    @Test(expected = JpaSystemException.class)
    public void usernameIsUnique() {
        createAndSaveUserEntity();
        createAndSaveUserEntity();
    }

    private UserEntity createAndSaveUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(USERNAME);
        entity.setPassword(PASSWORD);

        return userRepository.save(entity);
    }

}
