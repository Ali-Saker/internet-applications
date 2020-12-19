package com.internetapplications.mail;

public class TextEmail extends BaseEmail {

    private final String text;

    public TextEmail(String subject, String text) {
        super(subject);
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
