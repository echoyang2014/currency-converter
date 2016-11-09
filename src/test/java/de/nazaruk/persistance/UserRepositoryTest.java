package de.nazaruk.persistance;

import de.nazaruk.persistence.UserEntity;
import de.nazaruk.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @PersistenceContext
    private EntityManager entityManager;

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

    @Test
    public void trimUsername() {
        UserEntity entity = new UserEntity();
        entity.setUsername("  " + USERNAME + "  ");
        entity.setPassword(PASSWORD);

        entity = userRepository.save(entity);
        entityManager.clear();
        assertEquals(USERNAME, userRepository.findOne(entity.getId()).getUsername());
        assertEquals(USERNAME, userRepository.findByUsername(USERNAME).getUsername());
    }

    private UserEntity createAndSaveUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(USERNAME);
        entity.setPassword(PASSWORD);

        return userRepository.save(entity);
    }

}
