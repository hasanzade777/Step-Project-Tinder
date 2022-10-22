package dao.dao;

import entities.Identifiable;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A obj);
    Optional<A> get(Long id);
    Optional<A> get(A obj);
    List<A> getAll();
    Connection getConn();
}