package net.ckj46.JTM.tags.control;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tags.repository.TagsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TagsService {
    private TagsRepository tagsRepository;

    public void addTag(String name) {
        log.info("addTag {}", name);
        tagsRepository.addTag(new Tag(name));
    }

    public List<Tag> fetchAll() {
        log.info("fetchAll");
        return tagsRepository.fetchAll();
    }

    public Tag fetchById(Long tagId) {
        log.info("fetchById - tagId: {}", tagId);
        return tagsRepository.fetchById(tagId);
    }
}
