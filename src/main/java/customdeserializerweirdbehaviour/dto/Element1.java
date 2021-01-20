package customdeserializerweirdbehaviour.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Element1 {

  @JsonProperty("string1")
  private String string1;

  @JsonProperty("string2")
  private String string2;
}
