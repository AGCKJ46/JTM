package net.ckj46.JTM.tags.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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
}
