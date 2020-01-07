package net.ckj46.JTM.tags.repository;

import net.ckj46.JTM.tags.entity.Tag;

import java.util.List;

public interface TagsRepository {
    void addTag(Tag tag);
    List<Tag> fetchAll();
    Tag fetchById(Long id);
}
