package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GetLecturePathInterestUseCase extends UseCase<GetLecturePathInterestUseCase.Input, GetLecturePathInterestUseCase.Output>{
    private GetLecturesUseCase getLecturesUseCase;

    public GetLecturePathInterestUseCase(GetLecturesUseCase getLecturesUseCase) {
        this.getLecturesUseCase = getLecturesUseCase;
    }

    @Override
    public Output execute(Input input) {
        List<Lecture> lectures = getLecturesUseCase
                .execute(new GetLecturesUseCase.Input())
                .getLectures();

        int userNumber = lectures
                .parallelStream()
                .mapToInt(lecture -> lecture.getListeners().size())
                .sum();

        Map<Integer, Double> pathInterestMap = new HashMap<>();
        List<Integer> allPathNumbers = getAllPathNumbers(lectures);
        allPathNumbers.forEach(pathNum -> {
            int userNumberInPath = getUserNumberInPath(lectures, pathNum);
            pathInterestMap.put(pathNum, (double) (userNumberInPath / userNumber));
        });
        return new Output(pathInterestMap);
    }

    private List<Integer> getAllPathNumbers(List<Lecture> lectures){
        return lectures.stream()
                .map(Lecture::getPathNumber)
                .distinct()
                .collect(Collectors.toList());
    }

    private int getUserNumberInPath(List<Lecture> lectures, Integer pathNumber){
        return lectures.stream()
                .filter(lecture -> lecture.getPathNumber().equals(pathNumber))
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
