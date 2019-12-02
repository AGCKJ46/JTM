package net.ckj46.JTM;

import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.tasks.boundary.FileSystemStorageService;
import net.ckj46.JTM.tasks.boundary.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;


@Slf4j
@Configuration
public class JtmConfiguration {

    @Bean
    public Clock Clock(){
        log.info("Registering Clock as Spring Bean!");
        return new SystemClock();
    }

    @Bean
    public StorageService storageService(){
        log.info("Registering StorageService as Spring Bean!");
        return new FileSystemStorageService(Path.of("J:/JAVA/JTM_fsss"));
    }
}
