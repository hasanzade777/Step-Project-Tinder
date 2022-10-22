package services;

import entities.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long id);
    Optional<User> getUser(User user);
    Optional<User> getUser(String emailAddress, String password);
    List<User> getAllUsers();
    boolean userExistsById(Long id);
    void updateLastLoginDateTime(Long id);
    Connection getConn();
}
