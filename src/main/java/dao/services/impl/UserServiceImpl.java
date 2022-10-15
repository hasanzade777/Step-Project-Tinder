package dao.services.impl;

import dao.dao.DaoSql;
import dao.services.UserService;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;

public class UserServiceImpl implements UserService {
    private DaoSql<User> dao;

    public UserServiceImpl(DaoSql<User> dao) {
        this.dao = dao;
    }

    @Override
    public Optional<User> get(User user) {
        return dao.get(user);
    }

    @Override
    public Optional<User> get(String emailAddress, String password) {
        return dao.get(new User(emailAddress, password));
    }

    @Override
    public List<User> getAll() {
        return dao.getAll();
    }

    @Override
    @SneakyThrows
    public void updateLastLoginDateTime(Long id) {
        Connection conn = dao.getConn();
        String SQL = "UPDATE users SET last_login_date_time = NOW() WHERE id = ?";
        try (PreparedStatement psttm = conn.prepareStatement(SQL)) {
            psttm.setLong(1, id);
            psttm.executeUpdate();
        }
    }
}
