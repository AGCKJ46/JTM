package net.ckj46.JTM.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ckj46.JTM.app.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public void Comment(String text) {
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }
}
