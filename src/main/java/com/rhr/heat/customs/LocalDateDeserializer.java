package com.rhr.heat.customs;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalDateDeserializer implements JsonDeserializer < LocalDate > {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    throws JsonParseException {
        return LocalDate.parse(json.getAsString(),
            DateTimeFormatter.ofPattern("d-MMM-yyyy").withLocale(Locale.ENGLISH));
    }
}
