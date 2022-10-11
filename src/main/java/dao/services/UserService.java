package dao.services;

import dao.dao.DAO;
import entities.User;

import java.sql.PreparedStatement;
import java.util.Optional;

public class UserService {
    private DAO<User> dao;

    public UserService(DAO<User> dao) {
        this.dao = dao;
    }

    public Optional<User> get(User user) {
        return dao.get(user);
    }

    public Optional<User> get(String emailAddress, String password) {
        return dao.get(new User(emailAddress, password));
    }
}
