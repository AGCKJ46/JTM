package net.ckj46.JTM.attachments.repository;

import net.ckj46.JTM.attachments.entity.Attachment;
import java.util.List;

public interface AttachmentsRepository {
    void add(Attachment attachment);

    List<Attachment> fetchAll();

    Attachment fetchById(Long id);

    void deleteById(Long id);

    void save(Attachment attachment);
}
