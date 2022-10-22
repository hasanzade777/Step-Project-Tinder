package services;

import entities.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);
    List<Message> getAllMessagesBetween(Long user1, Long user2);
}
