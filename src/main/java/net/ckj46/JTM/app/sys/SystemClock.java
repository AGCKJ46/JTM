package net.ckj46.JTM.app.sys;

import net.ckj46.JTM.app.sys.Clock;

import java.time.LocalDateTime;

public class SystemClock implements Clock {
    @Override
    public LocalDateTime time() {
        return LocalDateTime.now();
    }
}
