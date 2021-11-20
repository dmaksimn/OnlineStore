package com.dmaksimn.store.service.impl;


import com.dmaksimn.store.domain.Cart;
import com.dmaksimn.store.domain.User;
import com.dmaksimn.store.enums.ResultEnum;
import com.dmaksimn.store.exception.AppException;
import com.dmaksimn.store.repository.CartRepository;
import com.dmaksimn.store.repository.UserRepository;
import com.dmaksimn.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public User findOneUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Collection<User> findUserByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    @Transactional
    public User saveNewUser(User user) {
        /**
         * Register New User
         */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userRepository.save(user);

            /**
             *  Initial Cart
             */

            Cart savedCart = cartRepository.save(new Cart(savedUser));
            savedUser.setCart(savedCart);
            return userRepository.save(savedUser);
        } catch (Exception e) {
            throw new AppException(ResultEnum.VALID_ERROR);
        }
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User oldUser = userRepository.findByEmail(user.getEmail());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setAddress(user.getAddress());
        return userRepository.save(oldUser);
    }
}
