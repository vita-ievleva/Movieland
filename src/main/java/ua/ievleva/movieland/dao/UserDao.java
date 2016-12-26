package ua.ievleva.movieland.dao;

import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.exception.InvalidCredentialsException;

import java.util.Map;


public interface UserDao {
    User findUserByCredentials(Map<String, String> credentials) throws InvalidCredentialsException;
}
