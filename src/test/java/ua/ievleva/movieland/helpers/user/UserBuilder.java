package ua.ievleva.movieland.helpers.user;


import ua.ievleva.movieland.entity.Role;
import ua.ievleva.movieland.entity.User;

import java.util.List;

public class UserBuilder {
    private String name;
    private String email;
    private String userName;
    private String password;
    private String enabled;
    private List<Role> role;

    private UserBuilder () {}

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder password(String password){
        this.password = password;
        return this;
    }
    public UserBuilder userName(String userName){
        this.userName = userName;
        return this;
    }

    public User build() {
        User user = new User();
        user.setName(name);
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        user.setEnabled(enabled);

        return user;
    }






}
