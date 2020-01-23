package net.ckj46.JTM.attachments.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "attachments")
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private LocalDateTime createdAt;

    // @Column(nullable = false)
    private Long taskId;
    // Set<Comment> comments = null;

    public Attachment(String fileName, Long tasks) {
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();
        this.taskId = tasks;
        // this.comments = new LinkedHashSet<>();
    }

// void addComment(String textComment){comments.add(new Comment());}
}
