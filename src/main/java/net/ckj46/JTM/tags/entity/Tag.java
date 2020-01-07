package net.ckj46.JTM.tags.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("tags")
public class Tag {
    @Id
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
