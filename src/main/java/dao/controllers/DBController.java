package dao.controllers;

import dao.dao.MessageDaoImpl;
import dao.dao.UserDaoImpl;
import entities.Message;
import entities.User;
import services.MessageService;
import services.UserService;
import services.impl.MessageServiceImpl;
import services.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DBController {
    private UserService us;
    private MessageService ms;

    public DBController(Connection conn) {
        this.us = new UserServiceImpl(new UserDaoImpl(conn));
        this.ms = new MessageServiceImpl(new MessageDaoImpl(conn));
    }

    public Optional<User> getUser(String emailAddress, String password) {
        return us.get(emailAddress, password);
    }

    public Optional<User> getUserById(Long id) {
        return us.getUserByID(id);
    }

    public List<User> getAllUsers() {
        return us.getAll();
    }

    public void updateLastLogin(Long id) {
        us.updateLastLoginDateTime(id);
    }

    public boolean userExistsById(Long id) {
        return getUserById(id).isPresent();
    }

    public void saveMessage(Message message) {
        try {
            ms.saveMessage(message);
        }
        catch (Exception e) {
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

    public static <A> A remapResult(ResultSet result, Function<ResultSet, A> f) {
        try {
            result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return f.apply(result);
    }

    public List<Message> getAllMessagesBetween(Long user1, Long user2) {
        return ms.getAllMessagesBetween(user1, user2);
    }
}
