package net.ckj46.JTM.comments;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("tasks")
@Slf4j
public class Comment {
    @Id
    private Long id;

    private String text;
    // private CommentType type;
    // private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public void Comment(String text) {
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }
}
