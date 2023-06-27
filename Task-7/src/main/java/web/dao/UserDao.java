package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    List<User> allUsers();

    void save(User user);

    void delete(User user);

    User getById(Long id);
}