package com.internetapplications.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseEmail {

    private final String subject;

    private final List<File> attachments = new ArrayList();

    public BaseEmail(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void addAttachment(File attachment) {
        this.attachments.add(attachment);
    }

    public List<File> getAttachments() {
        return Collections.unmodifiableList(this.attachments);
    }
}
