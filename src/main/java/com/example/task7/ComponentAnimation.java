package com.example.task7;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class ComponentAnimation implements Observer {
    private GraphicsContext graphicsContext;
    private int period = 10; // По умолчанию 10 секунд
    private int lastAnimatedTime = 0;
    private boolean isActive = false;
    private Timeline timeline;
    private AtomicReference<Double> progress = new AtomicReference<>(0.0); // Прогресс от 0.0 до 1.0

    public ComponentAnimation(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void update(Subject st) {
        if (isActive) {
            TimeServer timeServer = (TimeServer) st;
            if (timeServer.getState() - lastAnimatedTime >= period) {
                animate();
                lastAnimatedTime = timeServer.getState();
            }
        }
    }

    private void animate() {
        double width = graphicsContext.getCanvas().getWidth();
        double height = graphicsContext.getCanvas().getHeight();

        // Очистка холста
        graphicsContext.clearRect(0, 0, width, height);

        // Рисование фона прямолинейного индикатора
        graphicsContext.setStroke(Color.LIGHTGRAY);
        graphicsContext.setLineWidth(10);
        graphicsContext.strokeRect(0, height / 2 - 5, width, 10); // Фон индикатора

        // Рисование прогресса прямолинейного индикатора
        graphicsContext.setFill(Color.CORAL);
        graphicsContext.fillRect(0, height / 2 - 5, progress.get() * width, 10); // Заполнение прогресса

        // Создание анимации прогресса
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            try {
                double currentProgress = progress.get();
                if (currentProgress < 1.0) {
                    progress.set(currentProgress + 0.1); // Увеличиваем прогресс на 10% каждые 2 секунды
                } else {
                    progress.set(0.0); // Сбрасываем прогресс, если он достиг 100%
                }
                animate(); // Перерисовываем индикатор
            } catch (Exception e) {
                e.printStackTrace(); // Логирование исключения
                System.err.println("Ошибка в анимации: " + e.getMessage());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void start(int period) {
        this.period = period;
        isActive = true;
        animate(); // Запуск анимации сразу при старте
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
        isActive = false;
    }
}