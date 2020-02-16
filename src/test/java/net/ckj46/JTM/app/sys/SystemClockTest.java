package net.ckj46.JTM.app.sys;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SystemClockTest {
    Clock clock = new SystemClock();

    @Test

    public void shouldGiveCurrenttime(){
        // given
        LocalDateTime now = LocalDateTime.now();
        // when
        LocalDateTime clocksTime = clock.time();
        // then
        assertEquals(now.getDayOfYear(), clocksTime.getDayOfYear(), "Days should equal to each other!");
    }
}