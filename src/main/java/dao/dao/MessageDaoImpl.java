package dao.dao;

import dao.controllers.DBController;
import entities.Message;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MessageDaoImpl implements DAO<Message> {

    private Connection conn;

    public MessageDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    @SneakyThrows
    public void save(Message obj) {
        String SQL = "INSERT INTO messages  VALUES(DEFAULT,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setLong(1, obj.getFromId());
            ps.setLong(2, obj.getToId());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, obj.getMessage());
            ps.executeUpdate();
        }
    }

    @SneakyThrows
    @Override
    public Optional<Message> get(Long id) {
        String SQL = "SELECT * FROM message WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setLong(1, id);
            var result = ps.executeQuery();
            return result.isBeforeFirst() ? Optional.of(DBController.remapResult(result, Message::getFromResultSet)) :
                    Optional.empty();
        }
    }

    @Override
    public Optional<Message> get(Message msg) {
        throw new RuntimeException("not implemented");
    }

    @SneakyThrows
    @Override
    public List<Message> getAll() {
        String SQL = "SELECT * FROM message";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            var result = ps.executeQuery();
            return DBController.remapResultSet(result, Message::getFromResultSet);
        }
    }

    @Override
    public Connection getConn() {
        return conn;
    }
}