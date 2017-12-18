package com.springbazaar.service;

import com.springbazaar.domain.User;
import com.springbazaar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveOrUpdate(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(BigInteger id) {
        return userRepository.findOne(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
