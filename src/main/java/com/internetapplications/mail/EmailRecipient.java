package com.internetapplications.mail;

public class EmailRecipient {

    private final String name;

    private final String email;

    public EmailRecipient(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
