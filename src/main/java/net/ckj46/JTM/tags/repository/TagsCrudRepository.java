package net.ckj46.JTM.tags.repository;

import net.ckj46.JTM.tags.entity.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagsCrudRepository extends CrudRepository<Tag, Long> {
}
