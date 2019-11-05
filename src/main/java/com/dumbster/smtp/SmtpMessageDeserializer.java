package com.dumbster.smtp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SmtpMessageDeserializer extends JsonDeserializer<SmtpMessage> {
  @Override
  public SmtpMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    Map<String, List<String>> headers = new HashMap<>();
    //System.out.println("Headers");
    for (Iterator<Map.Entry<String,JsonNode>> it = node.get("headers").fields(); it.hasNext();) {
      Map.Entry<String,JsonNode> header = it.next();
      //System.out.println("Header is:" + header.getKey());
      List<String> values = new ArrayList<>();
      for (Iterator<JsonNode> entry = header.getValue().iterator(); entry.hasNext(); ) {
        JsonNode headVal = entry.next();
        //System.out.println("Adding " + header.getKey() + ": " + headVal.asText());
        values.add(headVal.asText());
      }
      headers.put(header.getKey(), values);
    }
    SmtpMessage msg = new SmtpMessage();
    msg.setHeaders(headers);
    msg.setBody(node.get("body").asText());
    return msg;
  }
}
