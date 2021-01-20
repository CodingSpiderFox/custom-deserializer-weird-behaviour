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
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    JsonNode outerArrayRootNode = jsonParser.getCodec().readTree(jsonParser);

    //parse the datetime format header element
    Iterator<JsonNode> iterator = outerArrayRootNode.path(0).iterator();
    JsonNode element1Node = iterator.next();
    ObjectMapper objectMapper = new ObjectMapper();
    result.setElement1(objectMapper.readValue(element1Node.toString(), Element1.class));

    //parse the parameter definition elements
    List<Element2> element2List = new ArrayList<>();
    while (iterator.hasNext()) {
      JsonNode singleElement2Node = iterator.next();

      try {
        element2List.add(objectMapper.readValue(singleElement2Node.toString(), Element2.class));
      } catch (IOException ex) {
      }
    }

    result.setElement2List(element2List);

    Map<String, List<BigDecimal>> tuples = new HashMap<>();

    //the amount on elements in the first inner array determines the amount of values in each of the subsequent arrays.
    //the first element in those subsequent arrays is always the DateTime string, then following the values measured at
    //that point in time

    int amountOfValuesForTimeStamp = getValueArrayCount(outerArrayRootNode);

    for (int i = 1; i <= amountOfValuesForTimeStamp; i++) {
      List<String> currentValueList = new ArrayList<>();
      JsonNode tupleNode = outerArrayRootNode.path(i);

      iterator = tupleNode.elements();
      while(iterator.hasNext()) {
        currentValueList.add(iterator.next().asText());
      }

      //the first element should always be timestamp. Use as key and the rest as numbers
      String dateTime = currentValueList.remove(0);
      tuples.put(dateTime, currentValueList.stream().map(s -> new BigDecimal(s)).collect(Collectors.toList()));
    }

    result.setValusOfTrailingArrays(tuples);

    return result;
  }

  private int getValueArrayCount(JsonNode outerArrayRootNode) {
    // The first array is always the parameter definitions
    return outerArrayRootNode.size() - 1;
  }
}
