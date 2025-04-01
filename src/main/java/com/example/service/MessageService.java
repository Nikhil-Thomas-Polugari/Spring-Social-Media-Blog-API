package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;

import com.example.entity.Message;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public int deleteMessage(int messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);  // Perform the actual deletion
            return 1;  // 1 indicates successful deletion
        }
        return 0;  // 0 indicates no message was found/deleted
    }
    
    public int updateMessage(int id, String newText) {
        Optional<Message> existingMessage = messageRepository.findById(id);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setMessageText(newText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByUser(int userId) {
        return messageRepository.findByPostedBy(userId);
    }

    public boolean existsById(int messageId) {
        return messageRepository.existsById(messageId);
    }
    
}


