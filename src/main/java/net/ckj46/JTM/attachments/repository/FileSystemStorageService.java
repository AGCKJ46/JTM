package net.ckj46.JTM.attachments.repository;

import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class FileSystemStorageService implements StorageService {
    private final Path path;

    public FileSystemStorageService(Path path) {
        this.path = path;
    }

    @Override
    public void saveFile(Long taskId, MultipartFile file) throws IOException {
        Path directoryPath = path.resolve(taskId.toString());

        if(!Files.isDirectory(directoryPath)){
            Files.createDirectory(directoryPath);
        }
        Path targetPath = directoryPath.resolve(file.getOriginalFilename());
        log.info("Attachment: {} is added to directory: {}", file.getOriginalFilename(), directoryPath);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public Resource loadFile(Long taskId, String filename) throws MalformedURLException {
        Path targetPath = path.resolve(taskId.toString());
        targetPath = targetPath.resolve(filename);
        return new UrlResource(targetPath.toUri());
    }

    /*
    @Override
    public Set<Attachment> fetchAllAttachments(Long taskId) throws IOException {
        Set<Attachment> attachments = new HashSet<>();

        Path directoryPath = path.resolve(taskId.toString());
        if( Files.isDirectory(directoryPath, LinkOption.NOFOLLOW_LINKS)) {
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(directoryPath);
            for (Path file: dirStream) {
                attachments.add(new Attachment(file.getFileName().toString()));
            }
        }
        return attachments;
    }
    */

    @Override
    public void deleteFile(String fileName, Long taskId) throws IOException {
        Path directoryPath = path.resolve(taskId.toString());
        Path targetPath = directoryPath.resolve(fileName);
        log.info("Attachment: {} is deleted from directory: {}", fileName, directoryPath);
        if(Files.isRegularFile(targetPath, LinkOption.NOFOLLOW_LINKS)){
            Files.delete(targetPath);
        }
    }
}
