package dao.dao;

import entities.Message;
import java.util.List;

public interface MessageDaoService {
    Long toWhoID(Long fromID);

    List<Message> getMessagesFrom(Long fromID, Long toID);

    void addMessage(Long fromID, Long toID, String message);
}
