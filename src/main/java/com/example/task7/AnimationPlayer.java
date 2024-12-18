package com.example.task7;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class AnimationPlayer implements Observer {
    private final Subject subject;
    private HelloController controller;
    private int start;
    private int interval;

    public AnimationPlayer(Subject subject, HelloController controller) {
        this.subject = subject;
        this.controller = controller;
        this.start = subject.getState();
    }

    public void startAnimationEvery(int interval) {
        this.interval = interval;
    }

    @Override
    public void update(Subject st) {
        int time = st.getState();
        if (time % interval == 0) {
            animateRectangle();
        }
    }

    private void animateRectangle() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), controller.getAnimationRectangle());
        transition.setByX(200);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.play();
    }
}