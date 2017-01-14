package ua.ievleva.movieland.service;


import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.exception.InvalidCredentialsException;

import java.util.Map;

public interface UserService {
    User findUserByCredentials(Map<String, String> credentials) throws InvalidCredentialsException;
}
