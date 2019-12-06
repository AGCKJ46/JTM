package net.ckj46.JTM.attachments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class FileSystemStorageService implements StorageService {
    private final Path path;

    public FileSystemStorageService(Path path) {
        this.path = path;
    }

    @Override
    public void saveFile(Long taskId, MultipartFile file) throws IOException {
        Path targetPath = path.resolve(taskId.toString());

        if(!Files.isDirectory(targetPath)){
            Files.createDirectory(targetPath);
        }
        targetPath = targetPath.resolve(file.getOriginalFilename());
        log.info("Adding attachment: {} is added in directory: {}", file.getOriginalFilename(), targetPath);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public Resource loadFile(Long taskId, String filename) throws MalformedURLException {
        Path targetPath = path.resolve(taskId.toString());
        targetPath = targetPath.resolve(filename);
        return new UrlResource(targetPath.toUri());
    }

    @Override
    public List<String> fetchAllAttachments(Long taskId){
        List<String> attachmentsList = new LinkedList<>();

        Path targetPath = path.resolve(taskId.toString());
        if( Files.isDirectory(targetPath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(targetPath);
                for (Path entry: dirStream) {
                    attachmentsList.add(entry.getFileName().toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                // TODO
            }
        }
        return attachmentsList;
    }
}
