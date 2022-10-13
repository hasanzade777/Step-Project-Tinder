package dao.services;

import dao.dao.DaoSql;
import entities.Message;

public class MessageService {
    private DaoSql<Message> dao;

    public MessageService(DaoSql<Message> dao) {
        this.dao = dao;
    }
}
