package dao.dao;

import entities.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DaoSqlUser extends DaoSql<User> {

    public DaoSqlUser(Connection conn) {
        super(conn);
    }

    @Override
    public void save(User obj) {
        throw new RuntimeException();
    }

    @Override
    public Optional<User> get(long id) {
        throw new RuntimeException();
    }

    @Override
    public Optional<User> get(User obj) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(long id) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(User obj) {
        throw new RuntimeException();
    }

    @Override
    public List<User> getAll() {
        throw new RuntimeException();
    }

    @Override
    public void saveAll(List<User> data) {
        throw new RuntimeException();
    }

    @Override
    public void setAll(List<User> data) {
        throw new RuntimeException();
    }
}
