package net.ckj46.JTM.tags.repository;

import net.ckj46.JTM.tags.entity.Tag;

import java.util.Set;

public interface TagsRepository {
    void addTag(Tag tag);
    Set<Tag> fetchAll();
    Tag fetchById(Long id);
}
