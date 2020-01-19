package net.ckj46.JTM.tags.repository;

import net.ckj46.JTM.tags.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsCrudRepository extends JpaRepository<Tag, Long> {
}
