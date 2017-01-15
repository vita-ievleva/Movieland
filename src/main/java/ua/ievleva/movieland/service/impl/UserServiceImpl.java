package ua.ievleva.movieland.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ievleva.movieland.dao.UserDao;
import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.exception.InvalidCredentialsException;
import ua.ievleva.movieland.service.UserService;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByCredentials(Map<String, String> credentials) throws InvalidCredentialsException {
        return userDao.findUserByCredentials(credentials);
    }
}
