package net.ckj46.JTM.attachments.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.attachments.repository.AttachmentsRepository;
import net.ckj46.JTM.attachments.repository.StorageService;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentsRepository attachmentsRepository;
    private final StorageService storageService;

    public void addAttachment(MultipartFile attachment, Task task) throws IOException {
        log.info("addAttachment: {}", attachment.getOriginalFilename());
        attachmentsRepository.add(new Attachment(attachment.getOriginalFilename(), task));
        storageService.saveFile(task.getId(), attachment);
    }

    public void delAttachment(Long attachmentId, String fileName, Long taskId) throws IOException {
        log.info("delAttachment - fileName: {}, attachmentId: {}, taskId: {}", fileName, attachmentId, taskId);
        attachmentsRepository.deleteById(attachmentId);
        storageService.deleteFile(fileName, taskId);
    }

    // TODO tu powinno byc kasowane wszystko co znajduje się w katalogu oraz sam katalog
    public void delAttachments(Set<Attachment> attachments, Long taskId) throws IOException {
        log.info("delAttachments - taskId: {}", taskId);
        for (Attachment a : attachments) {
            delAttachment(a.getId(), a.getFileName(), taskId);
        }
    }

    public Attachment fetchAttachmentById(Long attachmentId) {
        log.info("fetchAttachmentById - attachmentId: {}", attachmentId );
        Attachment attachment = attachmentsRepository.fetchById(attachmentId);
        return attachment;
    }
}
