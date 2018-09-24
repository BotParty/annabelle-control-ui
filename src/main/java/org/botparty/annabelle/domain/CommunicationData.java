package org.botparty.annabelle.domain;

public class CommunicationData {

    public CommunicationData() {

    }

    public CommunicationData(
        String sender,
        String recipient,
        String message  ) {
            this.sender = sender;
            this.recipient = recipient;
            this.message = message;
        }

    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return this.recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }




    private String sender;
    private String recipient;
    private String message;


}