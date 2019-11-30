package net.ckj46.JTM;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JtmConfiguration {

    @Bean
    public Clock Clock(){
        log.info("Registering Clock as Spring Bean!");
        return new SystemClock();
    }
}
