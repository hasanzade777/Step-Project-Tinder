package dao.dao;

import entities.Identifiable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class DaoSql<A extends Identifiable> implements DAO<A> {

    private Connection conn;

    public DaoSql(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }
}
