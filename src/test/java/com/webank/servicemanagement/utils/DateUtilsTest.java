package com.webank.servicemanagement.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void parseDurationToMinutesTest() throws Exception {
        assertThat(DateUtils.parseDurationToMinutes("3d2h5m")).isEqualTo(4445);
    }

    @Test
    public void formatDurationToReadableValueTest() throws Exception {
        assertThat(DateUtils.formatDurationToReadableValue(266701)).isEqualTo("3 Days 2 Hours 5 Minutes 1 Seconds");
    }

    
    
}
