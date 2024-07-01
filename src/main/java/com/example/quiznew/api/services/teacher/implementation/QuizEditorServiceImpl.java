package com.example.quiznew.api.services.teacher.implementation;

import com.example.quiznew.api.converters.teacher.QuizEditorConverter;
import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToCreateDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorRequestToEditDto;
import com.example.quiznew.api.dtos.teacher.QuizEditorResponseDto;
import com.example.quiznew.api.exceptions.BadRequestException;
import com.example.quiznew.api.exceptions.NotFoundException;
import com.example.quiznew.api.services.teacher.QuizEditorService;
import com.example.quiznew.api.services.helper.ServiceHelper;
import com.example.quiznew.store.entities.Question;
import com.example.quiznew.store.entities.Quiz;
import com.example.quiznew.store.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizEditorServiceImpl implements QuizEditorService {

    QuizRepository quizRepository;

    QuizEditorConverter quizConverter;

    ServiceHelper serviceHelper;

    @Transactional
    @Override
    public QuizEditorResponseDto createQuiz(QuizEditorRequestToCreateDto dto) {

        if (dto.getQuizName().isBlank()) {
            throw new BadRequestException("Quiz name can't be empty.");
        }

        if (dto.getQuestionsToAddId().isEmpty()) {
            throw new BadRequestException("You need to specify the id of the questions that you want to add to the quiz.");
        }

        if (dto.getQuestionsToAddId().size() >= 2) {
            serviceHelper.findDuplicatesInRequestAndThrow(dto.getQuestionsToAddId());
        }

        quizRepository
                .findQuizByQuizName(dto.getQuizName())
                .ifPresent(anotherQuiz -> {
                    throw new BadRequestException(
                            String.format("Quiz with the same name already exists with id %s.",
                                    anotherQuiz.getId()
                            )
                    );
                });

        Quiz quiz = quizRepository.saveAndFlush(
                Quiz.builder()
                        .quizName(dto.getQuizName())
                        .questionsInQuizList(serviceHelper.getNeededQuestionsByIdOrElseThrow(dto.getQuestionsToAddId()))
                        .build()
        );

        return quizConverter.convertToQuizDto(quiz);
    }

    @Transactional
    @Override
    public QuizEditorResponseDto editQuizById(Long quizId, QuizEditorRequestToEditDto dto) {

        Quiz quiz = serviceHelper.getQuizByIdOrElseThrow(quizId);

        Optional<String> optionalQuizName = Optional.ofNullable(dto.getOptionalQuizName())
                .filter(quizName -> !quizName.isBlank());
        Optional<List<Long>> optionalQuestionsToAddId = Optional.ofNullable(dto.getOptionalQuestionsToAddId());
        Optional<List<Long>> optionalQuestionsToDeleteId = Optional.ofNullable(dto.getOptionalQuestionsToDeleteId());

        if (optionalQuizName.isEmpty()
                && optionalQuestionsToAddId.isEmpty()
                && optionalQuestionsToDeleteId.isEmpty()) {
            throw new BadRequestException("Parameters for the change are not set");
        }

        if (optionalQuestionsToAddId.isPresent() && optionalQuestionsToDeleteId.isPresent()) {
            List<String> addedAndRemovedAtTheSameTimeIds =
                    optionalQuestionsToAddId
                            .map(questionsToAddId ->
                                    questionsToAddId
                                            .stream()
                                            .filter(id -> optionalQuestionsToDeleteId.get().contains(id))
                                            .map(String::valueOf)
                                            .collect(Collectors.toList()))
                            .orElse(Collections.emptyList());

            if (!addedAndRemovedAtTheSameTimeIds.isEmpty()) {
                throw new BadRequestException(
                        String.format("You cannot add and delete question(s) with id %s at the same time.",
                                String.join(", ", addedAndRemovedAtTheSameTimeIds))
                );
            }
        }

        optionalQuizName.ifPresent(quizName -> {
            quizRepository
                    .findQuizByQuizName(quizName)
                    .filter(anotherQuiz -> !Objects.equals(anotherQuiz.getId(), quizId))
                    .ifPresent(anotherQuiz -> {
                        throw new BadRequestException(
                                String.format(
                                        "Quiz with the same text already exists with id %s.",
                                        anotherQuiz.getId()
                                )
                        );
                    });

            quiz.setQuizName(quizName);
        });

        optionalQuestionsToAddId
                .ifPresent(questionsToAddId -> {

                            if (questionsToAddId.size() >= 2) {
                                serviceHelper.findDuplicatesInRequestAndThrow(questionsToAddId);
                            }

                            List<String> alreadyInQuizQuestionsId = quiz.getQuestionsInQuizList()
                                    .stream()
                                    .map(Question::getId)
                                    .filter(questionsToAddId::contains)
                                    .map(Objects::toString)
                                    .collect(Collectors.toList());

                            if (!alreadyInQuizQuestionsId.isEmpty()) {
                                throw new BadRequestException(
                                        String.format("Questions with id(s) %s is(are) already in the quiz",
                                                String.join(", ", alreadyInQuizQuestionsId))
                                );
                            }

                            quiz.getQuestionsInQuizList().addAll(
                                    serviceHelper.getNeededQuestionsByIdOrElseThrow(questionsToAddId)
                            );
                        }
                );

        optionalQuestionsToDeleteId
                .ifPresent(questionsToDeleteId -> {

                            if (questionsToDeleteId.size() >= 2) {
                                serviceHelper.findDuplicatesInRequestAndThrow(questionsToDeleteId);
                            }

                            List<String> notFoundQuestionsIdInQuiz =
                                    questionsToDeleteId
                                            .stream()
                                            .filter(id -> quiz.getQuestionsInQuizList()
                                                    .stream()
                                                    .map(Question::getId)
                                                    .noneMatch(existingId -> existingId.equals(id))
                                            )
                                            .map(String::valueOf)
                                            .collect(Collectors.toList());

                            if (!notFoundQuestionsIdInQuiz.isEmpty()) {
                                throw new BadRequestException(
                                        String.format("Questions with id(s) %s was(were) not found in the quiz",
                                                String.join(", ", notFoundQuestionsIdInQuiz))
                                );
                            }

                            quiz.getQuestionsInQuizList().removeAll(
                                    serviceHelper.getNeededQuestionsByIdOrElseThrow(questionsToDeleteId)
                            );
                        }
                );

        final Quiz editedQuiz = quizRepository.saveAndFlush(quiz);

        return quizConverter.convertToQuizDto(editedQuiz);
    }

    @Transactional
    @Override
    public QuizEditorResponseDto getQuizById(Long quizId) {

        Quiz quiz = serviceHelper.getQuizByIdOrElseThrow(quizId);

        return quizConverter.convertToQuizDto(quiz);
    }

    @Transactional
    @Override
    public List<QuizEditorResponseDto> getAllQuizzes() {

        List<Quiz> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) {
            throw new NotFoundException("Quizzes weren't found.");
        }

        return quizConverter.convertToQuizDtoList(quizzes);
    }

    @Transactional
    @Override
    public String deleteQuizById(Long quizId) {

        serviceHelper.getQuizByIdOrElseThrow(quizId);

        quizRepository.deleteById(quizId);

        return String.format("Quiz with id %s has been deleted.", quizId);
    }

    @Transactional
    @Override
    public String deleteAllQuizzes() {

        quizRepository.deleteAll();

        return "All quizzes have been deleted";
    }

}
