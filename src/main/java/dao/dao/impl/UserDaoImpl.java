package dao.dao.impl;

import dao.controllers.DBController;
import dao.dao.DAO;
import entities.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements DAO<User> {

    private final Connection conn;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(User obj) {
        throw new RuntimeException("Registration not implemented.");
    }

    @SneakyThrows
    @Override
    public Optional<User> get(Long id) {
        String SQL = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement psttm = conn.prepareStatement(SQL)) {
            psttm.setLong(1, id);
            ResultSet rs = psttm.executeQuery();
            return rs.isBeforeFirst() ?
                    Optional.of(DBController.remapResult(rs, User::getFromResultSet)) : Optional.empty();
        }
    }


    @SneakyThrows
    @Override
    public Optional<User> get(User user) {
        String SQL = "SELECT * FROM users WHERE email_address = (?) AND password = (?)";
        try (PreparedStatement psttm = conn.prepareStatement(SQL)) {
            psttm.setString(1, user.getEmailAddress());
            psttm.setString(2, user.getPassword());
            ResultSet rs = psttm.executeQuery();
            return rs.isBeforeFirst() ?
                    Optional.of(DBController.remapResult(rs, User::getFromResultSet)) : Optional.empty();
        }
    }

    @SneakyThrows
    @Override
    public List<User> getAll() {
        String SQL = "SELECT * FROM users";
        try (Statement sttm = conn.createStatement()) {
            ResultSet rs = sttm.executeQuery(SQL);
            return rs.isBeforeFirst() ? new ArrayList<>(DBController.remapResultSet(rs, User::getFromResultSet)) :
                    new ArrayList<>();
        }
    }

    @Override
    public Connection getConn() {
        return conn;
    }
}
