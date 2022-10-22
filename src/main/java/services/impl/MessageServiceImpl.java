package services.impl;

import dao.controllers.DBController;
import dao.dao.DAO;
import entities.Message;
import lombok.SneakyThrows;
import services.MessageService;

import java.sql.PreparedStatement;
import java.util.List;

public class MessageServiceImpl implements MessageService {
    private DAO<Message> dao;

    public MessageServiceImpl(DAO<Message> dao) {
        this.dao = dao;
    }

    public MessageServiceImpl() {
    }

    @Override
    public void saveMessage(Message message) {
        dao.save(message);
    }

    @Override
    @SneakyThrows
    public List<Message> getAllMessagesBetween(Long user1, Long user2) {
        String SQL = "SELECT * FROM messages WHERE from_id = ? and to_id = ? OR from_id = ? and to_id = ?" +
                "ORDER BY date_time_sent";
        try (PreparedStatement ps = dao.getConn().prepareStatement(SQL)) {
            ps.setLong(1, user1);
            ps.setLong(2, user2);
            ps.setLong(3, user2);
            ps.setLong(4, user1);
            var result = ps.executeQuery();
            return DBController.remapResultSet(result, Message::getFromResultSet);
        }
    }
}
