package com.adamjurcz.conferenceservice.core.usecases.lecture;

import com.adamjurcz.conferenceservice.core.domain.Lecture;
import com.adamjurcz.conferenceservice.core.usecases.UseCase;
import lombok.Value;

import java.util.List;

public class GetLecturesUseCase extends UseCase<GetLecturesUseCase.Input, GetLecturesUseCase.Output> {
    private LectureRepository lectureRepository;

    public GetLecturesUseCase(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public Output execute(Input input) {
        return new Output(lectureRepository.getAll());
    }

    @Value
    public static class Input implements UseCase.Input{
    }

    @Value
    public static class Output implements UseCase.Output{
        List<Lecture> lectures;
    }

}
