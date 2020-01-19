package net.ckj46.JTM.tags.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "tags_tasks")
@Entity
public class TagRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Long tags;

    public TagRef(Tag tag) {
        this.tags = tag.getId();
    }
}
