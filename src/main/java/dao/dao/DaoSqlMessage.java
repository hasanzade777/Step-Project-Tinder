package dao.dao;

import entities.Message;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DaoSqlMessage extends DaoSql<Message> {

    public DaoSqlMessage(Connection conn) {
        super(conn);
    }

    @Override
    public void save(Message obj) {
        throw new RuntimeException();
    }

    @Override
    public Optional<Message> get(long id) {
        throw new RuntimeException();
    }

    @Override
    public Optional<Message> get(Message obj) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(long id) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(Message obj) {
        throw new RuntimeException();
    }

    @Override
    public List<Message> getAll() {
        throw new RuntimeException();
    }

    @Override
    public void saveAll(List<Message> data) {
        throw new RuntimeException();
    }

    @Override
    public void setAll(List<Message> data) {
        throw new RuntimeException();
    }
}