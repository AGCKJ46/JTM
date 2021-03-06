package net.ckj46.JTM.attachments.repository;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file.system.storage")
@Getter
@Setter
@Data
public class FileSystemStorageConfigurationProperties {
    private String path;
}
