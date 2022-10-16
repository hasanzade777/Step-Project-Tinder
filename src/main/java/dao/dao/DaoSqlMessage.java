package dao.dao;

import dao.controllers.DBController;
import entities.Message;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class DaoSqlMessage extends DaoSql<Message> implements MessageDaoService {

    public DaoSqlMessage(Connection conn) {
        super(conn);
    }

    @SneakyThrows
    @Override
    public Optional<Message> get(Long id) {
        Connection conn = getConn();
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
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public List<Message> getAll() {
        Connection conn = getConn();
        String SQL = "SELECT * FROM message";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            var result = ps.executeQuery();
            return DBController.remapResultSet(result, Message::getFromResultSet);
        }
    }

    @Override
    @SneakyThrows
    public Long toWhoID(Long fromID) {
        Connection conn = getConn();
        String SQL = "SELECT * FROM messages WHERE from_id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setLong(1, fromID);
            var result = ps.executeQuery();
            return DBController.remapResult(result, Message::getFromResultSet).getToId();
        }
    }

    @Override
    @SneakyThrows
    public List<Message> getMessagesFrom(Long fromID, Long toID) {
        Connection conn = getConn();
        String SQL = "SELECT * FROM messages WHERE from_id = ? and to_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setLong(1, fromID);
            ps.setLong(2, toID);
            var result = ps.executeQuery();
            return DBController.remapResultSet(result, Message::getFromResultSet);
        }
    }
}