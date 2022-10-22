package services.impl;

import dao.dao.DAO;
import entities.User;
import lombok.SneakyThrows;
import services.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private DAO<User> dao;

    public UserServiceImpl(DAO<User> dao) {
        this.dao = dao;
    }

    public UserServiceImpl() {
    }


    @Override
    public Optional<User> getUser(Long id) {
        return dao.get(id);
    }

    @Override
    public Optional<User> getUser(User user) {
        return dao.get(user);
    }

    @Override
    public Optional<User> getUser(String emailAddress, String password) {
        return dao.get(new User(emailAddress, password));
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAll();
    }

    @Override
    public boolean userExistsById(Long id) {
        return getUser(id).isPresent();
    }

    @Override
    @SneakyThrows
    public void updateLastLoginDateTime(Long id) {
        Connection conn = getConn();
        String SQL = "UPDATE users SET last_login_date_time = NOW() WHERE id = ?";
        try (PreparedStatement psttm = conn.prepareStatement(SQL)) {
            psttm.setLong(1, id);
            psttm.executeUpdate();
        }
    }

    @Override
    public Connection getConn() {
        return dao.getConn();
    }
}
