package com.example.task7;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class ComponentMusic implements Observer {
    private int delay;
    private MediaPlayer mediaPlayer;
    private int lastPlayedTime = 0;
    private boolean isActive = false;

    public ComponentMusic(int delay) {
        this.delay = delay;
        try {
            File file = new File("src/main/resources/com/example/task7/m2.mp3");
            Media sound = new Media(file.toURI().toString());
            this.mediaPlayer = new MediaPlayer(sound);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка загрузки медиафайла: " + e.getMessage());
        }
    }

    @Override
    public void update(Subject st) {
        if (isActive) {
            TimeServer timeServer = (TimeServer) st;
            if (timeServer.getState() - lastPlayedTime >= delay) {
                mediaPlayer.play();
                lastPlayedTime = timeServer.getState();
            }
        }
    }

    public void start(int delay) {
        this.delay = delay;
        isActive = true;
    }

    public void stop() {
        mediaPlayer.stop();
        isActive = false;
    }
}