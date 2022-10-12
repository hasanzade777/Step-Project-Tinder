package dao.services;

import dao.controllers.DBController;
import dao.dao.DAO;
import dao.dao.DaoSql;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private DaoSql<User> dao;

    public UserService(DaoSql<User> dao) {
        this.dao = dao;
    }

    public Optional<User> get(User user) {
        return dao.get(user);
    }

    public Optional<User> get(String emailAddress, String password) {
        return dao.get(new User(emailAddress, password));
    }

    public List<User> getAll() {
        return dao.getAll();
    }

    public void updateLastLogin(long id) {
        Connection conn = dao.getConn();
        String SQL = "UPDATE users SET last_login_date_time = NOW() WHERE id = ?";
        try (PreparedStatement psttm = conn.prepareStatement(SQL)) {
            psttm.setLong(1, id);
            psttm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
