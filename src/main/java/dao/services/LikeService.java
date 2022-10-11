package dao.services;

import dao.dao.DAO;
import entities.Like;

public class LikeService {
    private DAO<Like> dao;

    public LikeService(DAO<Like> dao) {
        this.dao = dao;
    }
}
