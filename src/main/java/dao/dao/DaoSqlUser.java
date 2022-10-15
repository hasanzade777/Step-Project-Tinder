package dao.dao;

import dao.controllers.DBController;
import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;

public class DaoSqlUser extends DaoSql<User> {

    public DaoSqlUser(Connection conn) {
        super(conn);
    }

    @Override
    public Optional<User> get(long id) {
        throw new RuntimeException();
    }

    @SneakyThrows
    @Override
    public Optional<User> get(User user) {
        Connection conn = getConn();
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
        Connection conn = getConn();
        String SQL = "SELECT * FROM users";
        try (Statement sttm = conn.createStatement()) {
            ResultSet rs = sttm.executeQuery(SQL);
            return rs.isBeforeFirst() ? new ArrayList<>(DBController.remapResultSet(rs, User::getFromResultSet)) :
                    new ArrayList<>();
        }
    }
}
