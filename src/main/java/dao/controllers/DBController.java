package dao.controllers;

import dao.dao.DaoSqlMessage;
import dao.dao.DaoSqlUser;
import entities.Message;
import entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import services.impl.MessageServiceImpl;
import services.impl.UserServiceImpl;

public class DBController {
    private UserServiceImpl us;
    private MessageServiceImpl ms;

    public DBController(Connection conn) {
        this.us = new UserServiceImpl(new DaoSqlUser(conn));
        this.ms = new MessageServiceImpl(new DaoSqlMessage(conn));
    }

    public Optional<User> getUser(String emailAddress, String password) {
        return us.get(emailAddress, password);
    }

    public List<Message> getAllMessages(Long id) {
        return ms.getMessages(id);
    }

    public Optional<User> getUserByID(Long id) {
        return us.getUserByID(id);
    }

    public void addMessage(Long fromId, Long toId, String message) {
        try {
            ms.addMessage(fromId, toId, message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <A> List<A> remapResultSet(ResultSet result, Function<ResultSet, A> f) {
        List<A> data = new ArrayList<>();
        while (true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            A p = f.apply(result);
            data.add(p);
        }
        return data;
    }

    public List<User> getAllUsers() {
        return us.getAll();
    }


    public void updateLastLogin(Long id) {
        us.updateLastLoginDateTime(id);
    }

    public static <A> A remapResult(ResultSet result, Function<ResultSet, A> f) {
        try {
            result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return f.apply(result);
    }
}
