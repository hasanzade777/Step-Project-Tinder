package dao.services;

import dao.dao.DAO;
import entities.Message;

public class MessageService {
    private DAO<Message> dao;

    public MessageService(DAO<Message> dao) {
        this.dao = dao;
    }
}
