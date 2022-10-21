package services.impl;

import dao.dao.DaoSqlMessage;
import entities.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import services.MessageService;

public class MessageServiceImpl implements MessageService {
    private DaoSqlMessage dao;

    public MessageServiceImpl(DaoSqlMessage dao) {
        this.dao = dao;
    }

    public MessageServiceImpl() {
    }

    @Override
    public List<Message> getMessages(Long fromID) {
        var toID = dao.toWhoID(fromID);
        var messagesByMe = dao.getMessagesFrom(fromID, toID);
        var messagesToMe = dao.getMessagesFrom(toID, fromID);
        List<Message> listOfMessages = new ArrayList<>();
        listOfMessages.addAll(messagesByMe);
        listOfMessages.addAll(messagesToMe);
//        listOfMessages = listOfMessages.stream().sorted((l1, l2) -> (int) (l1.getId() - l2.getId())).collect(Collectors.toList());
        return listOfMessages.isEmpty() ? new ArrayList<>() : listOfMessages.stream().sorted((l1, l2) -> (int) (l1.getId() - l2.getId())).collect(Collectors.toList());
//        return listOfMessages.isEmpty() ? new ArrayList<>() : listOfMessages;
    }

    @Override
    public void addMessage(Long fromId, Long toId, String message) {
        dao.addMessage(fromId, toId, message);
    }
}
