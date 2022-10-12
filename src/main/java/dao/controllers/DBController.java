package dao.controllers;

import dao.dao.DaoSqlMessage;
import dao.dao.DaoSqlUser;
import dao.services.MessageService;
import dao.services.UserService;
import entities.User;

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
        return us.get(emailAddress, password);
    }

    public static <A> List<A> remapResultSet(ResultSet result, Function<ResultSet, A> f){
        List<A> data = new ArrayList<>();
        while (true) {
            try {
                if (!result.next()) break;
            }
            catch (SQLException e) {
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

    public void updateLastLogin(long id) {
        us.updateLastLogin(id);
    }

    public static <A> A remapResult(ResultSet result, Function<ResultSet, A> f){
        try {
            result.next();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return f.apply(result);
    }
}
