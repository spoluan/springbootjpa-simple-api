package taodigital.interview.jpa.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import taodigital.interview.jpa.demo.model.request.UserLoginRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUserLoginSuccess() throws Exception {

        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("user");
        request.setPassword("password");

        mockMvc.perform(
                    post("/user/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                ).andExpectAll(
                    status().isOk(),
                    content().string(Matchers.containsString("Success"))
                ).andDo(
                        result -> {
                            System.out.println(result.getResponse().getContentAsString());
                        }
                );
    }

    @Test
    void testUserLoginNotFound() throws Exception {

        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("userss");
        request.setPassword("password");

        mockMvc.perform(
                post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound(),
                content().string(Matchers.containsString("Not found"))
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }

    @Test
    void testGetUserSuccess() throws Exception {
        mockMvc.perform(
                get("/user/2e1273f4-f574-4807-929e-d7c002643981")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    System.out.println(result.getResponse().getContentAsString());
                }
        );
    }
}