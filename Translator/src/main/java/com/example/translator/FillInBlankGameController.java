package com.example.translator;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FillInBlankGameController {

    @FXML
    private Label questionLabel;

    @FXML
    private TextField answerField;

    @FXML
    private Label scoreLabel;

    @FXML
    private ImageView gameBackground;

    private List<String> questions;
    private List<String> correctAnswers;
    private int currentQuestionIndex;
    private int score = 0;


    @FXML
    public void initialize() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/anh/bg.jpg"));
        gameBackground.setImage(backgroundImage);

        questions = Arrays.asList(
                "Our Tet holiday will last for al_ _ _ _ 2 weeks.",
                "_ _ irt",
                "_ _ own (color)",
                "h_ _se (animal)",
                "_ _ anket"
                // Thêm câu hỏi còn lại ở đây
        );

        correctAnswers = Arrays.asList(
                "almost",
                "shirt",
                "brown",
                "horse",
                "blanket"
                // Thêm đáp án còn lại ở đây
        );

        currentQuestionIndex = 0;
        showCurrentQuestion();
        showScore();
    }

    @FXML
    private void restartGame() {
        score = 0;
        showScore();
        currentQuestionIndex = 0;
        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            questionLabel.setText(questions.get(currentQuestionIndex));
            answerField.clear();
        } else {
            showScore();
            showEndGameMenu();
        }
    }

    private void showEndGameMenu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hoàn thành tất cả câu hỏi");
        alert.setHeaderText("Bạn đã hoàn thành tất cả câu hỏi!");

        ButtonType playAgainButton = new ButtonType("Chơi lại");

        alert.getButtonTypes().setAll(playAgainButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == playAgainButton) {
            restartGame();
        }
    }

    private void showScore() {
        scoreLabel.setText("Điểm của bạn: " + score + " / " + (questions.size() * 10));
    }

    @FXML
    private void checkAnswer() {
        String userAnswer = answerField.getText().trim();
        String correctAnswer = correctAnswers.get(currentQuestionIndex);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            score += 10;
            showScore();
            alert.setTitle("Chính xác!");
            alert.setHeaderText(null);
            alert.setContentText("Câu trả lời của bạn là chính xác!");
        } else {
            alert.setTitle("Sai");
            alert.setHeaderText(null);
            alert.setContentText("Sai. Câu trả lời đúng là: " + correctAnswer);
        }

        alert.showAndWait();

        currentQuestionIndex++;
        showCurrentQuestion();
    }
}
