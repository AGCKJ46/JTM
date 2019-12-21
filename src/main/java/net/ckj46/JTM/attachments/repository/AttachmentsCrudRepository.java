package net.ckj46.JTM.attachments.repository;

import net.ckj46.JTM.attachments.entity.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentsCrudRepository extends CrudRepository<Attachment, Long> {
}
