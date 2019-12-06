package net.ckj46.JTM.attachments;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface StorageService {
    void saveFile(Long taskId, MultipartFile file) throws IOException;
    Resource loadFile(Long id, String filename) throws MalformedURLException;

    List<String> fetchAllAttachments(Long id);
}
