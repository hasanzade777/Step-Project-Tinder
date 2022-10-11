package dao.dao;

import entities.Like;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DaoSqlLike extends DaoSql<Like> {

    public DaoSqlLike(Connection conn) {
        super(conn);
    }

    @Override
    public void save(Like obj) {
        throw new RuntimeException();
    }

    @Override
    public Optional<Like> get(long id) {
        throw new RuntimeException();
    }

    @Override
    public Optional<Like> get(Like obj) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(long id) {
        throw new RuntimeException();
    }

    @Override
    public boolean remove(Like obj) {
        throw new RuntimeException();
    }

    @Override
    public List<Like> getAll() {
        throw new RuntimeException();
    }

    @Override
    public void saveAll(List<Like> data) {
        throw new RuntimeException();
    }

    @Override
    public void setAll(List<Like> data) {
        throw new RuntimeException();
    }
}
