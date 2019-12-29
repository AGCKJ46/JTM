package net.ckj46.JTM.app.sys;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface Clock {
    LocalDateTime time();

    default LocalDate date() {
        return time().toLocalDate();
    }
}
