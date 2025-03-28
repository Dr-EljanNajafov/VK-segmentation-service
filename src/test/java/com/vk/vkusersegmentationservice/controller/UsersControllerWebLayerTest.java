package com.vk.vkusersegmentationservice.controller;

import com.vk.vkusersegmentationservice.dto.UserDTO;
import com.vk.vkusersegmentationservice.model.User;
import com.vk.vkusersegmentationservice.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UsersControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setEmail("user@vk.com");
    }

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception{
        //Arrange
        user.setId(1L);
        when(userService.createUser(any(User.class))).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserDTO createdUser = new ObjectMapper().readValue(responseBodyAsString, UserDTO.class);

        //Assert
        Assertions.assertEquals(user.getEmail(), createdUser.getEmail());
        Assertions.assertNotNull(createdUser.getId(), "User id should not be empty");
    }

    @Test
    @DisplayName("Get user by ID")
    public void testGetUserById_whenUserIdProvided_returnsUserDetailsById() throws Exception {
        // Arrange
        Long id = 1L;
        user.setId(id);
        when(userService.getUserById(id)).thenReturn(user); // Мокаем вызов сервиса

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserDTO returnedUser = new ObjectMapper().readValue(responseBodyAsString, UserDTO.class);

        // Assert
        Assertions.assertEquals(user.getId(), returnedUser.getId());
        Assertions.assertEquals(user.getEmail(), returnedUser.getEmail());
    }

    @Test
    @DisplayName("Get all users")
    public void testGetAllUsers_returnsListOfUsers() throws Exception {
        // Arrange
        List<User> users = List.of(
                new User(1L, "user1@vk.com", LocalDateTime.now()),
                new User(2L, "user2@vk.com", LocalDateTime.now())
        );
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        List<UserDTO> returnedUsers = Arrays.asList(new ObjectMapper().readValue(responseBodyAsString, UserDTO[].class));

        // Assert
        Assertions.assertEquals(2, returnedUsers.size());
        Assertions.assertEquals(users.get(0).getId(), returnedUsers.get(0).getId());
        Assertions.assertEquals(users.get(1).getEmail(), returnedUsers.get(1).getEmail());
    }

    @Test
    @DisplayName("Update user")
    public void testUpdateUser_whenValidUserDetailsProvided_returnsUpdatedUser() throws Exception {
        // Arrange
        Long id = 1L;
        User updatedUser = new User(id, "updated@vk.com", null);
        when(userService.updateUser(eq(id), any(User.class))).thenReturn(updatedUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedUser));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserDTO returnedUser = new ObjectMapper().readValue(responseBodyAsString, UserDTO.class);

        // Assert
        Assertions.assertEquals(updatedUser.getId(), returnedUser.getId());
        Assertions.assertEquals(updatedUser.getEmail(), returnedUser.getEmail());
    }

    @Test
    @DisplayName("Delete user")
    public void testDeleteUser_whenUserIdProvided_deletesUserSuccessfully() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(userService).deleteUser(id);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(id);
    }
}
