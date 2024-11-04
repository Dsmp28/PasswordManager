package org.java.passwordmanager.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.isExpectedStartArrayToken()) {
            // Lee el array y convierte a LocalDateTime
            int[] dateTimeArray = p.readValueAs(int[].class);

            if (dateTimeArray.length >= 6) {
                int year = dateTimeArray[0];
                int month = dateTimeArray[1];
                int day = dateTimeArray[2];
                int hour = dateTimeArray[3];
                int minute = dateTimeArray[4];
                int second = dateTimeArray[5];
                int nano = dateTimeArray.length > 6 ? dateTimeArray[6] : 0;

                return LocalDateTime.of(year, month, day, hour, minute, second, nano);
            } else {
                throw new IOException("Array para LocalDateTime inválido. Se esperan al menos 6 elementos.");
            }
        } else {
            // Lee como cadena con zona horaria y convierte a LocalDateTime
            String date = p.getText();
            OffsetDateTime odt = OffsetDateTime.parse(date);  // Parse con zona horaria
            return odt.toLocalDateTime();  // Convertir a LocalDateTime sin zona horaria
        }
    }
}


