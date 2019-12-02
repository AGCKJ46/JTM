package net.ckj46.JTM.tasks.boundary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void saveFile(Long taskId, MultipartFile file) throws IOException;
}
