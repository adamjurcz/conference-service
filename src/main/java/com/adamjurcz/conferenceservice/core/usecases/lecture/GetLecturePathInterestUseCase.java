package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class GetLecturePathInterestUseCase extends UseCase<GetLecturePathInterestUseCase.Input, GetLecturePathInterestUseCase.Output>{
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private GetAllLecturesUseCase getAllLecturesUseCase;

    public GetLecturePathInterestUseCase(GetAllLecturesUseCase getAllLecturesUseCase) {
        this.getAllLecturesUseCase = getAllLecturesUseCase;
    }

    @Override
    public Output execute(Input input) {
        List<Lecture> lectures = getAllLecturesUseCase
                .execute(new GetAllLecturesUseCase.Input())
                .getLectures();

        int userNumber = lectures
                .parallelStream()
                .mapToInt(lecture -> lecture.getListeners().size())
                .sum();

        Map<Integer, Double> pathInterestMap = new HashMap<>();
        List<Integer> allPathNumbers = getAllPathNumbers(lectures);
        allPathNumbers.forEach(pathNum -> {
            int userNumberInPath = getUserNumberInPath(lectures, pathNum);
            Double pathInterestFactor = (double) userNumberInPath / userNumber * 100;
            pathInterestFactor = Double.parseDouble(decimalFormat.format(pathInterestFactor));

            pathInterestMap.put(pathNum, pathInterestFactor);
        });
        return new Output(pathInterestMap);
    }

    private List<Integer> getAllPathNumbers(List<Lecture> lectures){
        return lectures.stream()
                .map(Lecture::getPath_number)
                .distinct()
                .collect(Collectors.toList());
    }

    private int getUserNumberInPath(List<Lecture> lectures, Integer pathNumber){
        return lectures.stream()
                .filter(lecture -> lecture.getPath_number().equals(pathNumber))
                .mapToInt(lecture -> lecture.getListeners().size())
                .sum();
    }

    @Value
    public static class Input implements UseCase.Input{
    }

    @Value
    public static class Output implements UseCase.Output{
        Map<Integer, Double> pathInterestMap;
    }
}
