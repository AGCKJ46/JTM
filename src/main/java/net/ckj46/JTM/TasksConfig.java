package net.ckj46.JTM;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.task")
@Getter
@Setter
@Data
public class TasksConfig {
    private String endpointMessage;
}
