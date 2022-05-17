package com.kalanco.dictator.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Events {
    List<Event> events = new ArrayList<>();

    public Events(List<Event> events) {
        this.events = events;
    }

    public Events() {
    }

    public List<Event> getEvents() {
        return events;
    }
}
