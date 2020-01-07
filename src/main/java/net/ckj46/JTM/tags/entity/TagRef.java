package net.ckj46.JTM.tags.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("tags_tasks")
public class TagRef {
    Long tags;

    public TagRef() {
    }

    public TagRef(Tag tag) {
        this.tags = tag.getId();
    }
}
