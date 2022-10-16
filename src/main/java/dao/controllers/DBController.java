package dao.controllers;

import dao.dao.DaoSqlMessage;
import dao.dao.DaoSqlUser;
import dao.services.MessageService;
import dao.services.UserService;
import entities.User;
import lombok.SneakyThrows;

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
        this.us = new UserService(new DaoSqlUser(conn));
        this.ms = new MessageService(new DaoSqlMessage(conn));
    }

    public Optional<User> getUser(String emailAddress, String password) {
        return us.getUser(emailAddress, password);
    }

    public List<User> getAllUsers() {
        return us.getAllUsers();
    }

    public boolean loginIsCorrect(String emailAddress, String password) {
        return getUser(emailAddress, password).isPresent();
    }

    public void updateLastLoginDateTime(long userId) {
        us.updateLastLoginDateTime(userId);
    }

    public static <A> List<A> remapResultSet(ResultSet result, Function<ResultSet, A> f) {
        List<A> data = new ArrayList<>();
        try (result) {
            while (true) {
                if (!result.next()) break;
                A p = f.apply(result);
                data.add(p);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public static <A> A remapResult(ResultSet result, Function<ResultSet, A> f) {
        try (result) {
            result.next();
            return f.apply(result);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
