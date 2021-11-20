package com.dmaksimn.store.service.impl;

import com.dmaksimn.store.domain.User;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.CartRepository;
import com.dmaksimn.store.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsersServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CartRepository cartRepository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setPassword("password");
        user.setEmail("email@email.com");
        user.setName("Name");
        user.setPhone(123);
        user.setAddress("Address Test");
    }

    @Test
    public void createUserTest() {
        when(userRepository.save(user)).thenReturn(user);
        userService.saveNewUser(user);
        Mockito.verify(userRepository, Mockito.times(2)).save(user);
    }

    @Test(expected = AppException.class)
    public void createUserExceptionTest() {
        userService.saveNewUser(user);
    }

    @Test
    public void updateTest() {
        User oldUser = new User();
        oldUser.setEmail("email@test.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(oldUser);
        when(userRepository.save(oldUser)).thenReturn(oldUser);
        User userResult = userService.updateUser(user);
        assertThat(userResult.getName(), is(oldUser.getName()));
    }
}
