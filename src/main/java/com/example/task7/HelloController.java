package com.example.task7;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField timerField;
    @FXML
    private TextField repeatField;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField textField;
    @FXML
    private Button textStartButton;
    @FXML
    private Button textStopButton;
    @FXML
    private TextField bellField;
    @FXML
    private Button bellStartButton;
    @FXML
    private Button bellStopButton;
    @FXML
    private Button clockStartButton;
    @FXML
    private Button clockStopButton;
    @FXML
    private Canvas canvas;

    private TimeServer timeServer;
    private ComponentText componentText;
    private ComponentMusic componentMusic;
    private ComponentAnimation componentAnimation;

    @FXML
    public void initialize() {
        timeServer = new TimeServer();
        componentText = new ComponentText(textField);

        // Проверка на пустые строки и нечисловые значения
        int bellDelay = 5; // Значение по умолчанию
        if (!bellField.getText().isEmpty()) {
            try {
                bellDelay = Integer.parseInt(bellField.getText());
            } catch (NumberFormatException e) {
                statusLabel.setText("Ошибка: неверное значение для задержки звонка");
            }
        }
        componentMusic = new ComponentMusic(bellDelay);

        componentAnimation = new ComponentAnimation(canvas.getGraphicsContext2D());

        timeServer.attach(componentText);
        timeServer.attach(componentMusic);
        timeServer.attach(componentAnimation);
    }

    @FXML
    public void start() {
        try {
            timeServer.start();
            statusLabel.setText("Таймер активен");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Ошибка при запуске таймера: " + e.getMessage());
        }
    }

    @FXML
    public void stop() {
        timeServer.stop();
        statusLabel.setText("Таймер остановлен");
    }

    @FXML
    public void reset() {
        timeServer.reset();
        statusLabel.setText("Таймер сброшен");
    }

    @FXML
    public void startText() {
        componentText.start();
    }

    @FXML
    public void stopText() {
        componentText.stop();
    }

    @FXML
    public void startBell() {
        try {
            int bellDelay = Integer.parseInt(bellField.getText());
            componentMusic.start(bellDelay);
        } catch (NumberFormatException e) {
            statusLabel.setText("Ошибка: неверное значение для задержки звонка");
        }
    }

    @FXML
    public void stopBell() {
        componentMusic.stop();
    }

    @FXML
    public void startClock() {
        componentAnimation.start(10); // По умолчанию 20 секунд
    }

    @FXML
    public void stopClock() {
        componentAnimation.stop();
    }

    public Node getAnimationRectangle() {
        return canvas;
    }
}