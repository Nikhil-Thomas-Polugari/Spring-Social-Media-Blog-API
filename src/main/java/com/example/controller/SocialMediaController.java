package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    private static final Logger logger = Logger.getLogger(SocialMediaController.class.getName());

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid registration details");
        }
        Optional<Account> existingAccount = accountService.findByUsername(account.getUsername());
        if (existingAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        Account savedAccount = accountService.createAccount(account);
        return ResponseEntity.ok(savedAccount);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        Optional<Account> existingAccount = accountService.validateLogin(account.getUsername(), account.getPassword());
        if (existingAccount.isPresent()) {
            return ResponseEntity.ok(existingAccount.get());
        } else {
            logger.warning("Failed login attempt for username: " + account.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255 || !accountService.existsById(message.getPostedBy())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message data");
        }
        Message savedMessage = messageService.createMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }


    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        // Get the message before deletion to check its existence
        Optional<Message> deletedMessage = messageService.getMessageById(messageId);
        
        // Delete the message and get the count of deleted rows
        int deletedRows = messageService.deleteMessage(messageId);
        
        if (deletedRows > 0 && deletedMessage.isPresent()) {
            // Return status 200 with the count of deleted rows in the response body
            return ResponseEntity.ok(deletedRows); // Return only the count of rows deleted
        }
        
        // If no rows were deleted, return status 200 but with no content (empty response body)
        return ResponseEntity.ok().build();  // No message was found or deleted, return empty body
    }


    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByUser(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByUser(accountId);
        if (messages.isEmpty()) {
            // Return status 200 OK with an empty list, not 404
            return ResponseEntity.ok(messages);
        }
        return ResponseEntity.ok(messages);
    }


    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable int messageId, @RequestBody Message newMessage) {
        if (newMessage.getMessageText().isBlank() || newMessage.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message text");
        }
        
        Optional<Message> existingMessage = messageService.getMessageById(messageId);
        if (existingMessage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message not found");
        }
        
        int updatedRows = messageService.updateMessage(messageId, newMessage.getMessageText());
        return updatedRows > 0 ? ResponseEntity.ok(updatedRows) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
