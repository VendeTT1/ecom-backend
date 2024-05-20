package com.online.shop.ecombackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.online.shop.ecombackend.dto.Registration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the endpoints in the AuthenticationController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    /** Extension for mocking email sending. */
    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);
    /** The Mocked MVC. */
    @Autowired
    private MockMvc mvc;

    /**
     * Tests the register endpoint.
     * @throws Exception
     */
    @Test
    @Transactional
    public void testRegister() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Registration registration = new Registration();
        registration.setEmail("AuthenticationControllerTest$testRegister@junit.com");
        registration.setFirstname("FirstName");
        registration.setLastname("LastName");
        registration.setPassword("Password123");
        // Null or blank username.
        registration.setUsername(null);
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setUsername("");
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setUsername("AuthenticationControllerTest$testRegister");
        // Null or blank email.
        registration.setEmail(null);
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setEmail("");
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setEmail("AuthenticationControllerTest$testRegister@junit.com");
        // Null or blank password.
        registration.setPassword(null);
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setPassword("");
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setPassword("Password123");
        // Null or blank first name.
        registration.setFirstname(null);
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setFirstname("");
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setFirstname("FirstName");
        // Null or blank last name.
        registration.setLastname(null);
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setLastname("");
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
        registration.setLastname("LastName");
        //TODO: Test password characters, username length & email validity.
        // Valid registration.
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registration)))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
