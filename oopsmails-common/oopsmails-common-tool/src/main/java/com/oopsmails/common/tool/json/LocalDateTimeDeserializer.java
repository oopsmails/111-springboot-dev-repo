package com.oopsmails.common.tool.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    public static final long serialVersionUID = 1L;

    public LocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    public static void main(String[] args) {
        Instant instant = OffsetDateTime.parse("1998-11-22T20:55:33.666-00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant();
        System.out.println("To system default zone: " + LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        System.out.println("Post-Truncate: " + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(LocalDateTime.now().format(formatter));
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDateTime.ofInstant(OffsetDateTime.parse(jsonParser.readValueAs(String.class), DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant(), ZoneId.systemDefault());
    }
}
