package com.dumbster.smtp;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SmtpMessageSerializer extends StdSerializer<SmtpMessage> {

  public SmtpMessageSerializer() {
    this(null);
  }

  public SmtpMessageSerializer(Class<SmtpMessage> t) {
    super(t);
  }

  @Override
  public void serialize(SmtpMessage msg, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeObjectField("headers",  msg.getHeaders());
    jsonGenerator.writeStringField("body", msg.getBody());
    jsonGenerator.writeEndObject();
  }
}
