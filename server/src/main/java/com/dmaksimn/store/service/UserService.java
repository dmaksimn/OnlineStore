package com.dmaksimn.store.service;

import com.dmaksimn.store.domain.User;
import java.util.Collection;

public interface UserService {
    User findOneUserByEmail(String email);
    Collection<User> findUserByRole(String role);
    User saveNewUser(User user);
    User updateUser(User user);
}
