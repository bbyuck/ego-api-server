package hanta.bbyuck.egoapiserver.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


public class TimeCalculatorTest {


    @Test
    public void test() {
        LocalDateTime testDate1 = LocalDateTime.of(2020, 3, 21, 23, 59, 10);
        LocalDateTime testDate2 = LocalDateTime.of(2020, 3, 22, 0, 7, 10);
        Boolean t = TimeCalculator.isBeforeTenMinutes(testDate1, testDate2);

        Assertions.assertThat(t).isEqualTo(true);

    }

}