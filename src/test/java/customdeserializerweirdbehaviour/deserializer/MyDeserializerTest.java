package customdeserializerweirdbehaviour.deserializer;

import customdeserializerweirdbehaviour.dto.Element2;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import customdeserializerweirdbehaviour.service.ApiClientTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MyDeserializerTest {

  @Test
  public void deserializesCorrectly() {

    MyCustomDto dto = MyDeserializer.deserialize(ApiClientTest.JSON);
    assertThat(dto, instanceOf(MyDeserializer.class));
    assertThat(dto.getElement1().getString1(), is(equalTo("value1")));
    assertThat(dto.getElement1().getString2(), is(equalTo("value2")));

    assertThat(dto.getElement2List(), hasSize(3));

    Element2 element2 = dto.getElement2List().get(0);
    assertThat(element2.getString1(), is(equalTo("value1")));
    assertThat(element2.getString2(), is(equalTo("value2")));
    assertThat(element2.getString3(), is(equalTo("value3")));
    assertThat(element2.getString4(), is(equalTo("value4")));
    assertThat(element2.getString5(), is(equalTo("value5")));

    // assertThat(dto.getValusOfTrailingArrays().size(), is(equalTo(2)));
  }
}
