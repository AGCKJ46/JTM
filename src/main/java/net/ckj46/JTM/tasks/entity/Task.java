package net.ckj46.JTM.tasks.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.entity.BaseEntity;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tags.entity.Tag;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Setter
@Getter
@Slf4j
@NoArgsConstructor
@Table(name = "tasks")
@NamedEntityGraph(
    name = "Task.details",
    attributeNodes = { // definiujemy, które atrybuty chcemy pobierać dodatkowo?
            @NamedAttributeNode("attachments"),
            @NamedAttributeNode("tags")
    }
)
@Entity
public class Task extends BaseEntity {
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "projectId", nullable = false)
    private Project project;

    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST} )
    @JoinTable(
            name = "tags_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

    public Task(String title, String description, int prio, LocalDateTime createdAt, LocalDateTime editedAt, Project project) {
        this.title = title;
        this.description = description;
        this.prio = prio;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.project = project;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        log.info("Adding attachment: {} to task: {}", attachment.getFileName(), super.getId());
        this.attachments.add(attachment);
        log.info("Attachment: {} is added to task: {}", attachment.getFileName(), super.getId());
    }

    public void addTag(Tag tag) {
        log.info("Tag: {} is added to task: {}", tag.getId(), super.getId());
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        log.info("Tag: {} is removed from task: {}", tag.getId(), super.getId());
        this.tags.remove(tag);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project.toString() +
                ", prio=" + prio +
                ", createdAt=" + createdAt +
                ", editedAt=" + editedAt +
                // ", attachments=" + attachments +
                ", tags=" + tags +
                "} " + super.toString();
    }
}
