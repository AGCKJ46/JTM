package net.ckj46.JTM.tags.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "tags")
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public Tag(String name){
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", editedAt=" + editedAt +
                '}';
    }
}
