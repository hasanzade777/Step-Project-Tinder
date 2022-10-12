package dao.dao;

import dao.controllers.DBController;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;


public class DaoSqlUser extends DaoSql<User> {


    public DaoSqlUser(Connection conn) {
        super(conn);
    }

    @Override
    public void save(User user) {
        throw new RuntimeException();
    }

    @SneakyThrows
    @Override
    public Optional<User> get(long id) {
        Connection conn = getConn();
        String SQL = "SELECT * FROM users WHERE id= (?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet result = preparedStatement.executeQuery();
            return Optional.of(DBController.remapResult(result, User::getFromResultSet));
        }
    }

    @Override
    public Optional<User> get(User user) {
        Connection conn = getConn();
        String SQL = "SELECT * FROM users WHERE email_address = (?) AND password = (?)";
        try (PreparedStatement psttm = conn.prepareStatement(SQL)) {
            psttm.setString(1, user.getEmailAddress());
            psttm.setString(2, user.getPassword());
            ResultSet rs = psttm.executeQuery();
            return rs.isBeforeFirst() ? Optional.of(DBController.remapResult(rs, User::getFromResultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(long id) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(User user) {
        throw new RuntimeException();
    }

    @Override
    public List<User> getAll() {
        Connection conn = getConn();
        String SQL = "SELECT * FROM users";
        try (Statement sttm = conn.createStatement()) {
            ResultSet rs = sttm.executeQuery(SQL);
            if (!rs.isBeforeFirst()) {
                return new ArrayList<>();
            }
            return new ArrayList<>(DBController.remapResultSet(rs, User::getFromResultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<User> data) {
        throw new RuntimeException();
    }

    @Override
    public void setAll(List<User> data) {
        throw new RuntimeException();
    }
}
