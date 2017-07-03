package ua.ievleva.movieland.helpers.user;


import ua.ievleva.movieland.entity.User;

public class UserFactory {

    public static User aDefaultUser() {
        return UserBuilder.aUser().userName("default").password("123456").build();

    }
}
