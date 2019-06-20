package com.labs.maven.springBoot.SpringBootMSC.Model;

import javax.persistence.*;

@Entity
@Table(name="logging")
public class LoggingTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "messageType")
    private String messageType;

    @Column(name = "messageText")
    private String messageText;

    @Column(name = "entityName")
    private String entityName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public String getEntityName() {
        return entityName;
    }
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
