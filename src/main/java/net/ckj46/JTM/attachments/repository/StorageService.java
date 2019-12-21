package net.ckj46.JTM.attachments.repository;

import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

public interface StorageService {
    void saveFile(Long taskId, MultipartFile file) throws IOException;

    Resource loadFile(Long id, String filename) throws MalformedURLException;

    // Set<Attachment> fetchAllAttachments(Long taskId) throws IOException;

    void deleteFile(String fileName, Long taskId) throws IOException;
}
