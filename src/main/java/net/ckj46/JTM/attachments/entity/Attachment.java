package net.ckj46.JTM.attachments.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
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
    private Long tasks;
    // Set<Comment> comments = null;

    public Attachment(String fileName, Long tasks) {
        this.fileName = fileName;
        this.createdAt = LocalDateTime.now();
        this.tasks = tasks;
        // this.comments = new LinkedHashSet<>();
    }

// void addComment(String textComment){comments.add(new Comment());}
}
