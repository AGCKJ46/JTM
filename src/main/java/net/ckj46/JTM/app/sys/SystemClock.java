package net.ckj46.JTM.app.sys;

import java.time.LocalDateTime;

public class SystemClock implements Clock {
    @Override
    public LocalDateTime time() {
        return LocalDateTime.now();
    }
}
