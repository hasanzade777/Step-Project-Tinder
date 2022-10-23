package dao.controllers;

import dao.dao.impl.MessageDaoImpl;
import dao.dao.impl.UserDaoImpl;
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


    public Optional<User> getUser(Long id) {
        return us.getUser(id);
    }

    public Optional<User> getUser(String emailAddress, String password) {
        return us.getUser(emailAddress, password);
    }

    public List<User> getAllUsers() {
        return us.getAllUsers();
    }

    public void updateLastLoginDateTime(long userId) {
        us.updateLastLoginDateTime(userId);
    }

    public boolean loginIsCorrect(String emailAddress, String password) {
        return getUser(emailAddress, password).isPresent();
    }

    public boolean userExistsById(Long id) {
        return us.userExistsById(id);
    }

    public void saveMessage(Message message) {
        try {
            ms.saveMessage(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Message> getAllMessagesBetween(Long user1, Long user2) {
        return ms.getAllMessagesBetween(user1, user2);
    }

    public static <A> List<A> remapResultSet(ResultSet result, Function<ResultSet, A> f) {
        List<A> data = new ArrayList<>();
        try (result) {
            while (true) {
                if (!result.next()) break;
                A p = f.apply(result);
                data.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static <A> A remapResult(ResultSet result, Function<ResultSet, A> f) {
        try (result) {
            result.next();
            return f.apply(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConn() {
        try {
            us.getConn().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
