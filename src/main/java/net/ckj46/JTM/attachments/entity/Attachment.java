package net.ckj46.JTM.attachments.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Slf4j
@Table("attachments")
public class Attachment {

    @Id
    private Long id;
    private String fileName;
    private LocalDateTime createdAt;

    private int tasks = 1;
    // Set<Comment> comments = null;

    public Attachment(String fileName) {
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();
        // this.comments = new LinkedHashSet<>();
    }

// void addComment(String textComment){comments.add(new Comment());}
}
