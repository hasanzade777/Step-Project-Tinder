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
    public Optional<Message> get(long id) {
        throw new RuntimeException();
    }

    @Override
    public Optional<Message> get(Message obj) {
        throw new RuntimeException();
    }

    @Override
    public List<Message> getAll() {
        throw new RuntimeException();
    }

}