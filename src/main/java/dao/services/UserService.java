package dao.services;

import dao.dao.DAO;
import entities.User;

public class UserService {
    private DAO<User> dao;

    public UserService(DAO<User> dao) {
        this.dao = dao;
    }
}
