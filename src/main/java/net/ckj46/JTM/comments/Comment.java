package net.ckj46.JTM.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    private Long id;

    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public void Comment(String text) {
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }
}
