package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.util.List;

@Slf4j
public class TimeCalculator {

    private static final long MIN_MATCHING_TIME = 600;
    private static final long MIN_CANCEL_TIME = 60;

    public static Boolean isBeforeCancelTime(LocalDateTime start, LocalDateTime end) {
        int startDay = start.getDayOfYear();
        int startHour = start.getHour();
        int startMinute = start.getMinute();
        int startSecond = start.getSecond();
        int endDay = end.getDayOfYear();
        int endHour = end.getHour();
        int endMinute = end.getMinute();
        int endSecond = end.getSecond();

        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);



        if (startDay == endDay) {
            // 동일 날짜에 10분전인지 체크
            Duration duration = Duration.between(startTime, endTime);
            long seconds = duration.getSeconds();

            // 10분 전이면
            if (seconds < MIN_CANCEL_TIME) return true;
            else return false;
        }
        else if (endDay - startDay == 1) {
            // 12시 넘어간 후 10분전인지 체크
            Duration duration = Duration.between(endTime, startTime);
            long seconds = duration.getSeconds();

            // 23:50:00(start) - 00:00:00(endTime) > seconds
            return seconds > 86400 - MIN_CANCEL_TIME;
        }
        else return false;
    }

    public static Boolean isBeforeTenMinutes(LocalDateTime start, LocalDateTime end) {
        int startDay = start.getDayOfYear();
        int startHour = start.getHour();
        int startMinute = start.getMinute();
        int startSecond = start.getSecond();
        int endDay = end.getDayOfYear();
        int endHour = end.getHour();
        int endMinute = end.getMinute();
        int endSecond = end.getSecond();

        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);



        if (startDay == endDay) {
            // 동일 날짜에 10분전인지 체크
            Duration duration = Duration.between(startTime, endTime);
            long seconds = duration.getSeconds();

            // 10분 전이면
            if (seconds < MIN_MATCHING_TIME) return true;
            else return false;
        }
        else if (endDay - startDay == 1) {
            // 12시 넘어간 후 10분전인지 체크
            Duration duration = Duration.between(endTime, startTime);
            long seconds = duration.getSeconds();

            // 23:50:00(start) - 00:00:00(endTime) > seconds
            return seconds > 86400 - MIN_MATCHING_TIME;
        }
        else return false;
    }

    // 오늘의 추천 새로고침 오늘 누른 횟수
    public static Integer todayRefreshCount(List<LolRecommendationRefresh> latelyRefreshes) {
        Integer answer = 0;

        for (LolRecommendationRefresh refresh : latelyRefreshes) {
            System.out.println(refresh.getRefreshDatetime().getDayOfYear() + " / " + LocalDateTime.now().getDayOfYear());
            if (refresh.getRefreshDatetime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) answer++;
        }
        System.out.println(answer);
        return answer;
    }
}
