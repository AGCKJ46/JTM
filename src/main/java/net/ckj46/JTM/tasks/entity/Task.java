package net.ckj46.JTM.tasks.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.entity.Attachment;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Table("tasks")
@Slf4j
public class Task {

    @Id
    private Long id;

    private String title;
    private String description;
    private String project;
    // private String owner;
    // private String assignee;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    private Set<Attachment> attachments = new HashSet<>();

    public Task(String title, String description, String project, int prio, LocalDateTime createdAt, LocalDateTime editedAt) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.prio = prio;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        log.info("Attachment: {} is added to task: {}", attachment.getFileName(), id);
        this.attachments.add(attachment);
    }
}
