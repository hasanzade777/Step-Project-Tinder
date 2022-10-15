package dao.dao;

import entities.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;

public class DaoSqlMessage extends DaoSql<Message> {

    public DaoSqlMessage(Connection conn) {
        super(conn);
    }

    @SneakyThrows
    @Override
    public Optional<Message> get(Long id) {
        Connection conn = getConn();
        String SQL = "SELECT * FROM MESSAGE WHERE ID = (?)";
        try(PreparedStatement ps = conn.prepareStatement(SQL)){
            ps.setString(1,String.valueOf(id));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Message> get(Message msg) {
        return Optional.empty();
    }

    @Override
    public List<Message> getAll() {
        return new ArrayList<>();
    }

}