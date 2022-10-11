package dao.controllers;

import dao.dao.DaoSqlLike;
import dao.dao.DaoSqlMessage;
import dao.dao.DaoSqlUser;
import dao.services.LikeService;
import dao.services.MessageService;
import dao.services.UserService;

import java.sql.Connection;

public class DBController {
    private UserService us;
    private MessageService ms;
    private LikeService ls;

    public DBController(Connection conn) {
        this.us = new UserService(new DaoSqlUser(conn));
        this.ms = new MessageService(new DaoSqlMessage(conn));
        this.ls = new LikeService(new DaoSqlLike(conn));
    }
}
