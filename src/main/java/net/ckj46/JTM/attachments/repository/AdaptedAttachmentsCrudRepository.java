package net.ckj46.JTM.attachments.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.attachments.entity.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
@Repository
public class AdaptedAttachmentsCrudRepository implements AttachmentsRepository {
    private final AttachmentsCrudRepository attachmentsCrudRepository;

    @Override
    public void add(Attachment attachment) {
        log.info("add: {}", attachment.getFileName());
        attachmentsCrudRepository.save(attachment);
    }

    @Override
    public List<Attachment> fetchAll() {
        log.info("fetchAll");
        return StreamSupport.stream(attachmentsCrudRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    @Override
    public Attachment fetchById(Long id) {
        log.info("fetchById: {}", id);
        return attachmentsCrudRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find attachment with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        log.info("deleteById: {}", id);
        attachmentsCrudRepository
                .findById(id)
                .ifPresentOrElse(task -> attachmentsCrudRepository.deleteById(task.getId()), () -> {
                    throw new NotFoundException("Cannot find attachment with id: " + id);
                });
    }

    @Override
    public void save(Attachment attachment) {
        log.info("save: {}", attachment.getFileName());
        attachmentsCrudRepository.save(attachment);
    }
}
