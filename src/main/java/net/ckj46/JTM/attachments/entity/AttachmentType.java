package net.ckj46.JTM.attachments.entity;

public enum AttachmentType {
    PROJECTS_ATTACHMENT("P"), TASKS_ATTACHMENT("T");
    private String type;

    private AttachmentType(String type) {
        this.type = type;
    }
}
