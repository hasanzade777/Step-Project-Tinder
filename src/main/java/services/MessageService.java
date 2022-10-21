package services;

import entities.Message;
import java.util.List;

public interface MessageService {
    List<Message> getMessages(Long fromID);

    void addMessage(Long fromId, Long toId, String message);
}
