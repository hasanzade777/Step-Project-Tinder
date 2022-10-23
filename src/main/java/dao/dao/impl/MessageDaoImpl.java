package dao.dao.impl;

import dao.controllers.DBController;
import dao.dao.DAO;
import entities.Message;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDaoImpl implements DAO<Message> {
    private final Connection conn;

    public MessageDaoImpl(Connection conn) {
        this.conn = conn;
    }


    @Override
    @SneakyThrows
    public void save(Message obj) {
        String SQL = "INSERT INTO messages VALUES(DEFAULT, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setLong(1, obj.getFromId());
            ps.setLong(2, obj.getToId());
            ps.setTimestamp(3, Timestamp.valueOf(obj.getDateTimeSent()));
            ps.setString(4, obj.getMessage());
            ps.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
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
        return get(msg.getId());
    }

    @Override
    @SneakyThrows
    public List<Message> getAll() {
        String SQL = "SELECT * FROM message";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            var result = ps.executeQuery();
            return result.isBeforeFirst() ? DBController.remapResultSet(result, Message::getFromResultSet) :
                    new ArrayList<>();
        }
    }

    @Override
    public Connection getConn() {
        return conn;
    }
}