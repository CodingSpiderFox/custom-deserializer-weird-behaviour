package customdeserializerweirdbehaviour.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import customdeserializerweirdbehaviour.dto.Element1;
import customdeserializerweirdbehaviour.dto.Element2;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyDeserializer extends JsonDeserializer<MyCustomDto> {

  @SneakyThrows
  public static MyCustomDto deserialize(String json) {
    ObjectMapper mapper = new ObjectMapper();
    MyDeserializer deserializer = new MyDeserializer();
    InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    JsonParser parser = mapper.getFactory().createParser(stream);
    return deserializer.deserialize(parser, mapper.getDeserializationContext());
  }

  @Override
  @SneakyThrows
  public MyCustomDto deserialize(JsonParser jsonParser, DeserializationContext ctxt) {
    MyCustomDto result = new MyCustomDto();

    jsonParser.nextToken();
    jsonParser.nextToken();
    jsonParser.nextToken();
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);

    Iterator<JsonNode> iterator = node.iterator();
    JsonNode element1Node = iterator.next();
    ObjectMapper objectMapper = new ObjectMapper();
    result.setElement1(objectMapper.readValue(element1Node.toString(), Element1.class));

    List<Element2> element2List = new ArrayList<>();
    while (iterator.hasNext()) {
      JsonNode singleElement2Node = iterator.next();

      try {
        element2List.add(objectMapper.readValue(singleElement2Node.toString(), Element2.class));
      } catch (IOException ex) {
        // nothing to do here. We're just interested in the channel definitions
      }
    }

    result.setElement2List(element2List);
    //
    //    Map<String, String> tuples = new HashMap<>();
    //
    //    for (int i = 1; i < node.size(); i++) {
    //      JsonNode tupleNode = node.path(i);
    //      tuples.put(tupleNode.path(0).asText(), tupleNode.path(1).asText());
    //    }
    //
    //    result.setValusOfTrailingArrays(tuples);

    return result;
  }
}
