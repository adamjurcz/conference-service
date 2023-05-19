package com.adamjurcz.conferenceservice.dataproviders.filesystem;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.lecture.EmailNotificationService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private static final String FILE_NAME = "powiadomienia.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void sendNotification(String email, Lecture lecture) {
        LocalDateTime dateTime = LocalDateTime.now();
        String formattedDate = dateTime.format(DATE_FORMATTER);
        String formattedStartDate = lecture.getStart_time().format(DATE_FORMATTER);
        String notificationHeader = String.format("Data wyslania: %s\nDo: %s\n\n", formattedDate, email);
        String notificationBody = String.format("Dzien dobry!\n Zarezerwowales miejsce na wykladzie '%s',\n ktory sie odbedzie %s\n\n",
                lecture.getMain_subject(), formattedStartDate);

        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
            fileWriter.write(notificationHeader+notificationBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
