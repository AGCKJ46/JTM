package net.ckj46.JTM.tasks.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tags.entity.TagRef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
@NoArgsConstructor
@Table(name = "tasks")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String project;
    // private String owner;
    // private String assignee;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId")
    private Set<Attachment> attachments = new HashSet<>();
    private transient Set<TagRef> tagRefs = new HashSet<>();

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

    public void addTag(Tag tag) {
        log.info("Tag: {} is added to task: {}", tag.getId(), id);
        this.tagRefs.add(new TagRef(tag));
    }

    public void removeTag(Tag tag) {
        log.info("Tag: {} is removed from task: {}", tag.getId(), id);
        this.tagRefs.remove(new TagRef(tag));
    }
}
