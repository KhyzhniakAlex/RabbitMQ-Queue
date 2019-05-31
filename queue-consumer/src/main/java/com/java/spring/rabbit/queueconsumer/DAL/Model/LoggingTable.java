package com.java.spring.rabbit.queueconsumer.DAL.Model;

import javax.persistence.*;

@Entity
@Table(name="logging")
public class LoggingTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "MessageType", nullable = false)
    private String MessageType;

    @Column(name = "MessageText", nullable = false)
    private String MessageText;

    @Column(name = "EntityName")
    private String EntityName;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMessageType() {
        return MessageType;
    }
    public void setMessageType(String messageType) {
        MessageType = messageType;
    }
    public String getMessageText() {
        return MessageText;
    }
    public void setMessageText(String messageText) {
        MessageText = messageText;
    }
    public String getEntityName() {
        return EntityName;
    }
    public void setEntityName(String entityName) {
        EntityName = entityName;
    }
}
