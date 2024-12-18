package com.example.task7;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer implements Subject {
    private Timer timer;
    private TimerTask task;
    private int timeState = 0;
    private List<Observer> observers = new ArrayList<>();

    public TimeServer() {
        this.task = new TimerTask() {
            public void run() {
                tick();
            }
        };
    }

    private void tick() {
        timeState++;
        notifyAllObservers();
    }

    public int getState() {
        return timeState;
    }

    public void setState(int time) {
        this.timeState = time;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void start() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        timer.schedule(task, 1000, 1000); // Запуск таймера с интервалом в 1 секунду
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    public void reset() {
        stop();
        timeState = 0;
        notifyAllObservers();
    }
}