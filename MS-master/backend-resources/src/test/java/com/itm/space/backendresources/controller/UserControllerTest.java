package com.itm.space.backendresources.controller;

import com.itm.space.backendresources.BaseIntegrationTest;
import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.exception.BackendResourcesException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "User", password = "123q123e", authorities = "ROLE_MODERATOR")
public class UserControllerTest extends BaseIntegrationTest {
    @MockBean
    private Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realmItm;
    @Mock
    private RoleMappingResource roleMappingResource;
    @Mock
    private MappingsRepresentation mappingsRepresentation;

    private RealmResource realmResourceMock;
    private UsersResource usersResourceMock;
    private UserResource userResource;
    private UserRepresentation userRepresentationMock;
    private UUID testId;

    @BeforeEach
    void setUp() {
        realmResourceMock = mock(RealmResource.class);
        usersResourceMock = mock(UsersResource.class);
        userRepresentationMock = mock(UserRepresentation.class);
        userResource = mock(UserResource.class);
        testId = UUID.randomUUID();
    }

    @Test
    @SneakyThrows
    public void helloTest() {
        MockHttpServletResponse response = mvc.perform(get("/api/users/hello"))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User", response.getContentAsString());
    }

    @Test
    @SneakyThrows
    public void createUserInvalidTest() {
        MockHttpServletResponse response = mvc.perform(requestWithContent(post("/api/users"),
                        new UserRequest(
                                "",
                                "notEmail",
                                "12345678",
                                "666",
                                "LKmsfa")))
                .andDo(print())
                .andExpect(jsonPath("$.username").value("Username should not be blank"))
                .andExpect(jsonPath("$.email").value("Email should be valid"))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @SneakyThrows
    public void createUserTest() {
        when(keycloak.realm(realmItm)).thenReturn(realmResourceMock);
        when(realmResourceMock.users()).thenReturn(usersResourceMock);
        when(usersResourceMock.create(any())).thenReturn(Response.status(Response.Status.CREATED).build());
        when(userRepresentationMock.getId()).thenReturn(UUID.randomUUID().toString());

        MockHttpServletResponse response = mvc.perform(requestWithContent(post("/api/users"),
                        new UserRequest(
                                "RazumOff",
                                "SR@email.ru",
                                "12345678",
                                "Sergei",
                                "Razumovski")))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(keycloak).realm(realmItm);
        verify(realmResourceMock).users();
        verify(usersResourceMock).create(any(UserRepresentation.class));
    }

    @Test
    @SneakyThrows
    public void getUserByIdTest() {
        when(keycloak.realm(realmItm)).thenReturn(realmResourceMock);
        when(realmResourceMock.users()).thenReturn(usersResourceMock);
        when(usersResourceMock.get(String.valueOf(testId))).thenReturn(userResource);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(String.valueOf(testId));
        userRepresentation.setFirstName("Sergei");
        userRepresentation.setLastName("Razumovski");
        userRepresentation.setEmail("SR@email.ru");

        when(userResource.toRepresentation()).thenReturn(userRepresentation);
        when(userResource.roles()).thenReturn(roleMappingResource);
        when(roleMappingResource.getAll()).thenReturn(mappingsRepresentation);

        MockHttpServletResponse response = mvc.perform(get("/api/users/" + testId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Sergei"))
                .andReturn()
                .getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @SneakyThrows
    @ExceptionHandler(BackendResourcesException.class)
    public void getUserById_UserNotFound_Test() {
        when(keycloak.realm("realm")).thenReturn(realmResourceMock);
        when(realmResourceMock.users()).thenReturn(usersResourceMock);
        when(usersResourceMock.get(eq(String.valueOf(testId)))).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/api/users/{id}", testId))
                .andDo(print())
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

    }

}