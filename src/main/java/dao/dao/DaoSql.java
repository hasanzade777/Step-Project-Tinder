package dao.dao;

import entities.Identifiable;
import java.sql.Connection;
import java.util.Optional;

public abstract class DaoSql<A extends Identifiable> implements DAO<A> {

    private Connection conn;

    public DaoSql(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public abstract Optional<A> get(Long id);
}
