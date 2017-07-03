package ua.ievleva.movieland.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.exception.InvalidCredentialsException;
import ua.ievleva.movieland.security.token.TokenCache;
import ua.ievleva.movieland.security.token.TokenUtils;
import ua.ievleva.movieland.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static ua.ievleva.movieland.helpers.movie.MovieFactory.EMPTY_MAP;
import static ua.ievleva.movieland.helpers.movie.MovieFactory.INVALID_CREDENTIALS_MAP;
import static ua.ievleva.movieland.helpers.movie.MovielandTestConstants.TOKEN;
import static ua.ievleva.movieland.helpers.user.UserFactory.aDefaultUser;

public class AuthenticationControllerTest {
    private static AuthenticationController authenticationControllerSUT;

    private TokenUtils tokenUtilsMock = mock(TokenUtils.class);
    private UserService userServiceMock = mock(UserService.class);
    private TokenCache tokenCacheMock = mock(TokenCache.class);
    private User userMock = aDefaultUser();

    @Before
    public void setUp() {
        authenticationControllerSUT = new AuthenticationController(tokenUtilsMock, userServiceMock, tokenCacheMock);
        ReflectionTestUtils.setField(authenticationControllerSUT, "expiration", "2");

        doReturn(TOKEN).when(tokenUtilsMock).createToken(userMock, 2);
    }

    @Test
    public void authenticateUser() throws Exception {
        doReturn(userMock).when(userServiceMock).findUserByCredentials(EMPTY_MAP);

        ResponseEntity result = authenticationControllerSUT.authenticateUser(EMPTY_MAP);

        assertThat("Status code should be 200", result.getStatusCode().is2xxSuccessful());
        verify(tokenUtilsMock, times(1)).createToken(userMock, 2);
        verify(tokenCacheMock, times(1)).addAuthorizedUser(userMock.getUserName(), TOKEN);
    }

    @Test
    public void authenticateUserInvalidCredentials() throws Exception {
        doThrow(new InvalidCredentialsException("Invalid password or username was provided."))
            .when(userServiceMock).findUserByCredentials(INVALID_CREDENTIALS_MAP);

        ResponseEntity result = authenticationControllerSUT.authenticateUser(INVALID_CREDENTIALS_MAP);

        assertThat("Status code should be UNAUTHORIZED", result.getStatusCode().is4xxClientError());
        assertThat("", result.getBody().equals("Invalid password or username was provided."));

        verifyZeroInteractions(tokenUtilsMock, tokenCacheMock);
    }
}