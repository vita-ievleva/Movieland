package ua.ievleva.movieland.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ReviewControllerTest extends MovieLandIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void addReviewSuccess() throws Exception {
        MvcResult mvcResult  = this.mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .content("{\"username\": \"tommy\", \"password\": \"123456\"}").contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String token = JsonPath.read(mvcResult.getResponse().getContentAsString(), "token");
        System.out.println(token);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/review").header("Authorization", token)
                .content("{\"username\": \"tommy\", \"id_movie\": \"1\", \"review_text\": \"This movie is great!\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value(true));
    }

    @Test
    public void addReviewFail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/review").header("Authorization", "token")
                .content("{\"username\": \"tommy\", \"id_movie\": \"1\", \"review_text\": \"This movie is great!\"}")
                .contentType(MediaType.APPLICATION_JSON));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/review")//.header("Authorization", "token")
                .content("{\"username\": \"tommy\", \"id_movie\": \"1\", \"review_text\": \"This movie is great!\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}