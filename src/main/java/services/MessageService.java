package services;

import entities.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessages(Long fromID);
}
