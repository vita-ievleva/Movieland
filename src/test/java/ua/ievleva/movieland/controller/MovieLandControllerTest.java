package ua.ievleva.movieland.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieLandControllerTest extends MovieLandIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testServletContextProvidesMovielandController() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("movieLandController"));
    }

    @Test
    public void getAllMovies() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].price").value(123.45))
                .andExpect(jsonPath("$.[0].yearOfRelease").value("1994"))
                .andExpect(jsonPath("$.[24].price").value(100.0))
                .andExpect(jsonPath("$.[24].yearOfRelease").value("1976"));
    }

   @Test
    public void getAllMoviesWithSpecificCurrency() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movies?currency=USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].price").value(Matchers.closeTo(4.57, 0.01)));
    }

    @Test
    public void getAllMoviesWithSortingPrice() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movies?price=desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].price", greaterThan(200.00)));
    }

    @Test
    public void getAllMoviesWithSortingRate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movies?rate=desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].rating", greaterThan(8.00)));
    }

    @Test
    public void getAllMoviesWithSortingRateAndPrice() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movies?price=desc&rate=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].rating", Matchers.is(8.6)))
                .andExpect(jsonPath("$.[0].price", Matchers.is(200.6)));
    }

    @Test
    public void getMovieById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(134.67))
                .andExpect(jsonPath("$.title").value("Зеленая миля/The Green Mile"));
    }

    @Test
    public void getMovieByIdWithSpecificCurrency() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie/1?currency=USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(Matchers.closeTo(4.99, 0.01)));
    }

    @Test
    public void searchMovies() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/search")
                .content("{\"genre\": \"комедия\", \"country\": \"США\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].price").value(170.00))
                .andExpect(jsonPath("$.[0].title").value("Джанго освобожденный/Django Unchained"));
    }



}