package ua.ievleva.movieland.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ua.ievleva.movieland.dao.UserDao;
import ua.ievleva.movieland.dao.mapper.UserMapper;
import ua.ievleva.movieland.entity.User;
import ua.ievleva.movieland.security.token.TokenUtils;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static ua.ievleva.movieland.helpers.movie.MovieFactory.EMPTY_MAP;
import static ua.ievleva.movieland.helpers.user.UserFactory.aDefaultUser;


public class UserDaoImplTest {
    private UserDao userDaoSUT;
    private TokenUtils tokenUtilsMock = mock(TokenUtils.class);
    private NamedParameterJdbcTemplate jdbcTemplateMock = mock(NamedParameterJdbcTemplate.class);
    private Properties sqlQueriesMock = mock(Properties.class);

    private User user = aDefaultUser();

    @Before
    public void setUp() {
        userDaoSUT = new UserDaoImpl(tokenUtilsMock, jdbcTemplateMock, sqlQueriesMock);

        doReturn(user).when(jdbcTemplateMock).queryForObject(anyString(), any(MapSqlParameterSource.class), any(UserMapper.class));
        doReturn(true).when(tokenUtilsMock).isCorrectPassword(anyString(), anyString());

        when(tokenUtilsMock.isCorrectPassword("", ""))
                .thenReturn(true);
    }

    @Test
    public void findUserByCredentials() throws Exception {
        assertThat(userDaoSUT.findUserByCredentials(EMPTY_MAP).getUserName(), is("default"));
    }

}