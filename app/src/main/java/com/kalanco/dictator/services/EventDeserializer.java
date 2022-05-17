package com.kalanco.dictator.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.kalanco.dictator.models.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventDeserializer implements JsonDeserializer<List<Event>> {
    @Override

    public List<Event> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("events");
        List<Event> events = new ArrayList<>();

        for (JsonElement event : jsonArray) {
            events.add(context.deserialize(event, Event.class));
        }
        return events;
    }
}
