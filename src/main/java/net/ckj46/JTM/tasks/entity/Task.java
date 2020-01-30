package net.ckj46.JTM.tasks.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tags.entity.Tag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Slf4j
@NoArgsConstructor
@Table(name = "tasks")
@NamedEntityGraph(
    name = "Task.details",
    attributeNodes = {
            @NamedAttributeNode("attachments"),
            @NamedAttributeNode("tags")
    }
)

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(nullable = false)
    private Long projectId;

    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskId")
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tags_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Task(String title, String description, Long projectId, int prio, LocalDateTime createdAt, LocalDateTime editedAt) {
        this.title = title;
        this.description = description;
        this.projectId = projectId;
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
        log.info("Adding attachment: {} to task: {}", attachment.getFileName(), id);
        this.attachments.add(attachment);
        log.info("Attachment: {} is added to task: {}", attachment.getFileName(), id);
    }

    public void addTag(Tag tag) {
        log.info("Tag: {} is added to task: {}", tag.getId(), id);
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        log.info("Tag: {} is removed from task: {}", tag.getId(), id);
        this.tags.remove(tag);
    }
}
