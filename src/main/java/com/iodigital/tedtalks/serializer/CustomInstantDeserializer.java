package com.iodigital.tedtalks.serializer;

import java.io.IOException;
import java.time.Instant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.iodigital.tedtalks.utils.Instants;

public class CustomInstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser json, DeserializationContext context) throws IOException {
        return Instant.from(Instants.FORMATTER.parse(json.getText()));
    }
}
