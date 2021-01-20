package customdeserializerweirdbehaviour.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import customdeserializerweirdbehaviour.dto.Element1;
import customdeserializerweirdbehaviour.dto.Element2;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MyDeserializer extends JsonDeserializer<MyCustomDto> {

  @SneakyThrows
  public static MyCustomDto deserialize(String json) {
    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(json, MyCustomDto.class);
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
      }
    }

    result.setElement2List(element2List);

//    Map<String, List<BigDecimal>> tuples = new HashMap<>();
//    List<BigDecimal> currentValueList = new ArrayList<>();
//
//    for (int i = 1; i < node.size(); i++) {
//      JsonNode tupleNode = node.path(i);
//      currentValueList.add(new BigDecimal(tupleNode.path(1).asText()));
//
//      //if()
//      //tuples.put(tupleNode.path(0).asText(), );
//    }
//
//    result.setValusOfTrailingArrays(tuples);

    return result;
  }
}
