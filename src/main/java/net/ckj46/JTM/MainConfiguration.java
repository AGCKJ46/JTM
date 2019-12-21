package net.ckj46.JTM;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.repository.FileSystemStorageConfigurationProperties;
import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.app.sys.SystemClock;
import net.ckj46.JTM.attachments.repository.FileSystemStorageService;
import net.ckj46.JTM.attachments.repository.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class MainConfiguration {

    private final FileSystemStorageConfigurationProperties fileSystemStorageConfigurationProperties;

    @Bean
    public Clock Clock(){
        log.info("Registering Clock as Spring Bean!");
        return new SystemClock();
    }


    @Bean
    public StorageService storageService(){
        log.info("Registering StorageService as Spring Bean!");
        return new FileSystemStorageService(Path.of(fileSystemStorageConfigurationProperties.getPath()));
    }
}
