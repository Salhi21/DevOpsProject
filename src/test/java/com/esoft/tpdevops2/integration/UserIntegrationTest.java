package com.esoft.tpdevops2.integration;

import com.esoft.tpdevops2.entities.User;
import com.esoft.tpdevops2.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Tests d'intégration User")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    @Test
    @Order(1)
    @DisplayName("Scénario complet: CRUD utilisateur")
    void fullCrudScenario() throws Exception {
        // 1. CREATE
        User newUser = new User(null, "Ahmed", "Ali");
        String userJson = objectMapper.writeValueAsString(newUser);

        // Perform POST and get the created user from response
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ahmed"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);
        Long userId = createdUser.getId();

        // Verify in database
        assertThat(userRepository.count()).isEqualTo(1);

        // 2. READ
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ahmed"))
                .andExpect(jsonPath("$.lastName").value("Ali"));

        // 3. UPDATE
        User updated = new User(userId, "Ahmed Updated", "Ali Updated");
        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ahmed Updated"))
                .andExpect(jsonPath("$.lastName").value("Ali Updated"));

        // 4. DELETE
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());

        assertThat(userRepository.count()).isEqualTo(0);
    }
}
