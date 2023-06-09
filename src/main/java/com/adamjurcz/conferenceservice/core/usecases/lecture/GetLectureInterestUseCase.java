package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetLectureInterestUseCase extends UseCase<GetLectureInterestUseCase.Input, GetLectureInterestUseCase.Output>{
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private GetAllLecturesUseCase getAllLecturesUseCase;

    public GetLectureInterestUseCase(GetAllLecturesUseCase getAllLecturesUseCase) {
        this.getAllLecturesUseCase = getAllLecturesUseCase;
    }

    @Override
    public Output execute(Input input) {
        List<Lecture> lectures = getAllLecturesUseCase
                .execute(new GetAllLecturesUseCase.Input())
                .getLectures();

        int usersNumber = lectures
                .parallelStream()
                .mapToInt(lecture -> lecture.getListeners().size())
                .sum();

        Map<Lecture,Double> lectureInterestMap = new HashMap<>();
        lectures.forEach(lecture -> {
            Double interestFactor = (double)lecture.getListeners().size()/usersNumber*100;
            interestFactor = Double.parseDouble(decimalFormat.format(interestFactor));

            lectureInterestMap.put(lecture, interestFactor);
        });

        return new Output(lectureInterestMap);
    }

    @Value
    public static class Input implements UseCase.Input{

    }

    @Value
    public static class Output implements UseCase.Output{
        Map<Lecture, Double> lectureDoubleMap;
    }
}
