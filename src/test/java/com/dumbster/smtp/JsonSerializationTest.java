package com.dumbster.smtp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

public class JsonSerializationTest {

  @Test
  public void testJsonSerialization() throws JsonProcessingException {
    SmtpMessage msg = new SmtpMessage();
    msg.addHeader("From", "per@alipsa.se");
    msg.addHeader("To", "some.mail@mail.com");
    msg.addHeader("To", "another.mail@another.com");
    msg.addHeader("Subject", "An important message");
    msg.setBody("Hello World");

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(msg);

    SmtpMessage deserialized = mapper.readValue(json, SmtpMessage.class);

    assertThat(msg.getBody(), equalTo(deserialized.getBody()));
    assertThat(msg.getHeaderValue("From"), equalTo(deserialized.getHeaderValue("From")));

    for(Map.Entry<String, List<String>> headers : msg.getHeaders().entrySet()) {
      String key = headers.getKey();
      assertTrue("missing key " + key + " in deserialized headers: " + deserialized.getHeaderNames(), deserialized.getHeaderNames().contains(key));
      List<String> deserializedValues = deserialized.getHeaderValues(key);
      for(String headerValue : headers.getValue()) {
        assertTrue("headerValue " + headerValue + " not found in " + deserializedValues, deserializedValues.contains(headerValue));
      }
    }
  }
}
