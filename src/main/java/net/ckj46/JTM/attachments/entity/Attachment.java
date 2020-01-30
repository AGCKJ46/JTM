package net.ckj46.JTM.attachments.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ckj46.JTM.tasks.entity.Task;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "attachments")
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "taskId", nullable = false)
    private Task task;
    // Set<Comment> comments = null;

    public Attachment(String fileName, Task task) {
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();
        this.task = task;
        // this.comments = new LinkedHashSet<>();
    }

// void addComment(String textComment){comments.add(new Comment());}
}
