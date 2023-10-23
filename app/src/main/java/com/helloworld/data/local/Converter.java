package com.helloworld.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.helloworld.domain.entities.Links;
import com.helloworld.domain.entities.Rocket;

public class Converter {
    private final Gson gson = new Gson();

    @TypeConverter
    public String fromRocket(Rocket rocket) {
        return gson.toJson(rocket);
    }

    @TypeConverter
    public Rocket toRocket(String value) {
        return gson.fromJson(value, new TypeToken<Rocket>() {
        }.getType());
    }

    @TypeConverter
    public String fromLinks(Links links) {
        return gson.toJson(links);
    }

    @TypeConverter
    public Links toLinks(String value) {
        return gson.fromJson(value, new TypeToken<Links>() {
        }.getType());
    }
}
