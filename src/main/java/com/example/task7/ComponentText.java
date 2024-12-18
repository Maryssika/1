package com.example.task7;

import javafx.scene.control.TextField;

public class ComponentText implements Observer {
    private TextField textField;
    private boolean isActive = false;

    public ComponentText(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void update(Subject st) {
        if (isActive) {
            TimeServer timeServer = (TimeServer) st;
            textField.setText("Прошло " + timeServer.getState() + " с");
        }
    }

    public void start() {
        isActive = true;
    }

    public void stop() {
        isActive = false;
    }
}