package net.ckj46.JTM;

import java.time.LocalDate;
import java.time.LocalDateTime;


public interface Clock {
    LocalDateTime time();

    default LocalDate date(){
        return time().toLocalDate();
    };
}
