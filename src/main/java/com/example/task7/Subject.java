package com.example.task7;

public interface Subject {
    void notifyAllObservers();
    void attach(Observer obs);
    void detach(Observer obs);

    int getState();
}