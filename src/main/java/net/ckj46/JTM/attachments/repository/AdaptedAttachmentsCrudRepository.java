package net.ckj46.JTM.attachments.repository;

import lombok.RequiredArgsConstructor;
import net.ckj46.JTM.attachments.entity.Attachment;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AdaptedAttachmentsCrudRepository implements AttachmentsRepository {
    AdaptedAttachmentsCrudRepository adaptedAttachmentsCrudRepository;

    @Override
    public void add(Attachment attachment) {

    }

    @Override
    public List<Attachment> fetchAll() {
        return null;
    }

    @Override
    public Attachment fetchById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(Attachment attachment) {

    }
}
