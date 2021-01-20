package customdeserializerweirdbehaviour.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import customdeserializerweirdbehaviour.deserializer.MyDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using = MyDeserializer.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyCustomDto {

  private Element1 element1;

  private List<Element2> element2List;

  private Map<String, List<BigDecimal>> valusOfTrailingArrays;
}
