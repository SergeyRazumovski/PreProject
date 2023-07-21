package service;

import model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserService {
    private static final String URL_API = "http://94.198.50.185:7081/api/users/";

    private final RestTemplate restTemplate = new RestTemplate();
    private final User user = new User(3L, "James", "Brown", (byte) 5);
    private final HttpHeaders requestHeaders = new HttpHeaders();

    private String key;

    public void inWork() {
        findAll();
        createUser();
        updateUser();
        deleteUser();
        getKey();
    }

    private void findAll() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL_API, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        String sessionId = httpHeaders.getFirst("Set-Cookie");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("Cookie", sessionId);
        System.out.println("findAllKey " + key);
    }

    private void createUser() {
        HttpEntity<User> entityPost = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(URL_API, HttpMethod.POST, entityPost, String.class);
        key = response.getBody();
        System.out.println("createUserKey " + key);
    }

    private void updateUser() {
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> entityPut = new HttpEntity<>(user, requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(URL_API, HttpMethod.PUT, entityPut, String.class);

        key += response.getBody();
        System.out.println("updateKey " + key);
    }

    private void deleteUser() {
        HttpEntity<Object> entityDelete = new HttpEntity<>(requestHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                URL_API + "/" + user.getId(), HttpMethod.DELETE, entityDelete, String.class);
        key += response.getBody();
        System.out.println("deleteKey " + key);
    }

    public String getKey() {
        return "getKey " + key;
    }
}
