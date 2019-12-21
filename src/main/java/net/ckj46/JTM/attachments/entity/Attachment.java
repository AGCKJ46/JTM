package net.ckj46.JTM.attachments.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.comments.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Table("attachments")
@Slf4j
public class Attachment {

    @Id
    private Long id;
    private String filename;
    // private AttachmentType type;
    // private Long ownerId;
    private LocalDateTime createdAt;
    // Set<Comment> comments = null;

    public Attachment(String filename) {
        this.filename = filename;
        this.createdAt = LocalDateTime.now();
        // this.comments = new LinkedHashSet<>();
    }

    // void addComment(String textComment){comments.add(new Comment());}
}
