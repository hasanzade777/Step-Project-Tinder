package dao.dao;

import entities.Identifiable;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A obj);
    Optional<A> get(long id);
    Optional<A> get(A obj);
    boolean remove(long id);
    boolean remove(A obj);
    List<A> getAll();
    void saveAll(List<A> data);
    void setAll(List<A> data);
}