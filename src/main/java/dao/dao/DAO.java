package dao.dao;

import entities.Identifiable;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    Optional<A> get(long id);
    Optional<A> get(A obj);
    List<A> getAll();
}