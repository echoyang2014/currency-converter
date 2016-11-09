package de.nazaruk.services.impl;

import de.nazaruk.persistence.UserEntity;
import de.nazaruk.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by nazaruk on 11/9/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserSecurityImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void save() {
        String password = "password";
        String encodedPassword = "encodedPassword";

        UserEntity user = new UserEntity();
        user.setPassword(password);
        when(bCryptPasswordEncoder.encode(password)).thenReturn(encodedPassword);

        user.setPassword(encodedPassword);

        userService.save(user);
        verify(userRepository).save(user);
    }

    @Test
    public void findByUsername() {
        UserEntity userEntity = mock(UserEntity.class);
        String username = "test";
        when(userRepository.findByUsername(username)).thenReturn(userEntity);

        assertEquals(userEntity, userService.findByUsername(username));
        verify(userRepository).findByUsername(username);
    }

}
