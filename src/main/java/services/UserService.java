package services;

import entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> get(User user);

    Optional<User> get(String emailAddress, String password);

    List<User> getAll();

    void updateLastLoginDateTime(Long id);

    Optional<User> getUserByID(Long id);
}
