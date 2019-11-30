package net.ckj46.JTM;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class SystemClock implements Clock {
    @Override
    public LocalDateTime time() {
        return LocalDateTime.now();
    }
}
