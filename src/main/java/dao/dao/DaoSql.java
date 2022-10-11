package dao.dao;

import entities.Identifiable;

import java.sql.Connection;

public abstract class DaoSql<A extends Identifiable> implements DAO<A> {

    private Connection conn;

    public DaoSql(Connection conn) {
        this.conn = conn;
    }
}
