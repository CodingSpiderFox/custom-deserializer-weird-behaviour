package customdeserializerweirdbehaviour.deserializer;

import customdeserializerweirdbehaviour.dto.Element2;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import customdeserializerweirdbehaviour.service.ApiClientTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MyDeserializerTest {

  @Test
  public void deserializesCorrectly() {

    MyCustomDto dto = MyDeserializer.deserialize(ApiClientTest.JSON);
    assertThat(dto, instanceOf(MyCustomDto.class));
    assertThat(dto.getElement1().getString1(), is(equalTo("Time")));
    assertThat(dto.getElement1().getString2(), is(equalTo("DateTimeFormat")));

    assertThat(dto.getElement2List(), hasSize(3));

    Element2 element2 = dto.getElement2List().get(0);
    assertThat(element2.getString1(), is(equalTo("value1")));
    assertThat(element2.getString2(), is(equalTo("value2")));
    assertThat(element2.getString3(), is(equalTo("value3")));
    assertThat(element2.getString4(), is(equalTo("value4")));
    assertThat(element2.getString5(), is(equalTo("value5")));


    Element2 element2_2 = dto.getElement2List().get(1);
    assertThat(element2_2.getString1(), is(equalTo("value1_2")));
    assertThat(element2_2.getString2(), is(equalTo("value2_2")));
    assertThat(element2_2.getString3(), is(equalTo("value3_2")));
    assertThat(element2_2.getString4(), is(equalTo("value4_2")));
    assertThat(element2_2.getString5(), is(equalTo("value5_2")));


    Element2 element2_3 = dto.getElement2List().get(2);
    assertThat(element2_3.getString1(), is(equalTo("value1_3")));
    assertThat(element2_3.getString2(), is(equalTo("value2_3")));
    assertThat(element2_3.getString3(), is(equalTo("value3_3")));
    assertThat(element2_3.getString4(), is(equalTo("value4_3")));
    assertThat(element2_3.getString5(), is(equalTo("value5_3")));

    assertThat(dto.getValusOfTrailingArrays().size(), is(equalTo(2)));

    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString1").get(0), is(equalTo(new BigDecimal("12"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString1").get(1), is(equalTo(new BigDecimal("0"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString1").get(2), is(equalTo(new BigDecimal("32.12"))));

    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString2").get(0), is(equalTo(new BigDecimal("14"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString2").get(1), is(equalTo(new BigDecimal("5"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString2").get(2), is(equalTo(new BigDecimal("333.12"))));
  }
}
