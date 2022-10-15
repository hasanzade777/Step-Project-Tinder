package dao.services.impl;

import dao.dao.DaoSql;
import entities.Message;

public class MessageServiceImpl {
    private DaoSql<Message> dao;

    public MessageServiceImpl(DaoSql<Message> dao) {
        this.dao = dao;
    }


}
