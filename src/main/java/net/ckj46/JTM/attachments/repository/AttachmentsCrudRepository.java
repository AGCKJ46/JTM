package net.ckj46.JTM.attachments.repository;

import net.ckj46.JTM.attachments.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentsCrudRepository extends JpaRepository<Attachment, Long> {
}
