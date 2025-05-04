package com.example.qysqaserver.mappers;

import com.example.qysqaserver.dto.response.UserResponse;
import com.example.qysqaserver.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.qysqaserver.constants.ValueConstants.ZONE_ID;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "accuracy", source = "user", qualifiedByName = "getAccuracy")
    @Mapping(target = "wasPlayedYesterday", source = "user", qualifiedByName = "wasPlayedYesterday")
    @Mapping(target = "wasPlayedToday", source = "user", qualifiedByName = "wasPlayedToday")
    @Mapping(target = "fireDays", source = "user", qualifiedByName = "fireDays")
    @Mapping(target = "joinedDate", expression = "java(user.getCreatedDate().toLocalDate())")
    public abstract UserResponse toResponse(User user);

    @Named("wasPlayedYesterday")
    public boolean wasPlayedYesterday(User user) {
        LocalDateTime lastPlayedAt = user.getLastPlayedAt();
        if (lastPlayedAt == null) return false;


        LocalDate yesterday = LocalDate.now(ZONE_ID).minusDays(1);
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();
        LocalDateTime yesterdayEnd = yesterday.atTime(LocalTime.MAX);

        return lastPlayedAt.isAfter(yesterdayStart) && lastPlayedAt.isBefore(yesterdayEnd);
    }

    @Named("wasPlayedToday")
    public boolean wasPlayedToday(User user) {
        LocalDateTime lastPlayedAt = user.getLastPlayedAt();
        if (lastPlayedAt == null) return false;

        LocalDate today = LocalDate.now(ZONE_ID);
        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime todayEnd = today.atTime(LocalTime.MAX);

        return lastPlayedAt.isAfter(todayStart) && lastPlayedAt.isBefore(todayEnd);
    }

    @Named("getAccuracy")
    public double getAccuracy(User user) {
        int questions = user.getAnsweredQuestionsCount();
        int rightAnswers = user.getRightAnswers();

        if (rightAnswers == 0) return 0;
        double temp = ((double) questions / (double) rightAnswers);

        if (temp == 0) return 0;

        var result = 100.0 / ((double) questions / (double) rightAnswers);

        return Math.round(result * 100.0) / 100.0;
    }

    @Named("fireDays")
    public int getFireDays(User user) {
        if (user.getLastPlayedAt() == null || user.getFireDays() == 0) {
            return 0;
        }

        LocalDateTime todayStart = LocalDate.now(ZONE_ID).atStartOfDay();
        LocalDateTime yesterdayStart = todayStart.minusDays(1);

        if (isDateTodayOrAfter(user.getFireDaysUpdatedAt(), todayStart) ||
                isDateTodayOrAfter(user.getLastPlayedAt(), todayStart)) {
            return user.getFireDays();
        }

        if (user.getLastPlayedAt().isBefore(yesterdayStart)) {
            user.setFireDays(0);
            return 0;
        }

        return user.getFireDays();
    }

    public boolean isDateTodayOrAfter(LocalDateTime date, LocalDateTime todayStart) {
        return date.isAfter(todayStart) || date.isEqual(todayStart);
    }
}
