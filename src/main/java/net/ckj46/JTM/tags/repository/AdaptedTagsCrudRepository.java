package net.ckj46.JTM.tags.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.tags.entity.Tag;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Slf4j
@Primary
@RequiredArgsConstructor
@Repository
public class AdaptedTagsCrudRepository implements TagsRepository{
    private final TagsCrudRepository tagsCrudRepository;

    @Override
    public void addTag(Tag tag) {
        log.info("addTag {}: ", tag);
        tagsCrudRepository.save(tag);
    }

    @Override
    public List<Tag> fetchAll() {
        return StreamSupport.stream(tagsCrudRepository.findAll().spliterator(), false).collect(toList());
    }

    @Override
    public Tag fetchById(Long id) {
        Tag tag = tagsCrudRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find tag with id: " + id));
        log.info("fetchTagById - tag: {}", tag.toString());
        return tag;
    }
}
