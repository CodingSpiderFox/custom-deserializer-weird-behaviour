package customdeserializerweirdbehaviour.service;

import customdeserializerweirdbehaviour.CustomdeserializerWeirdBehaviourApplication;
import customdeserializerweirdbehaviour.dto.Element2;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({ApiClient.class, CustomdeserializerWeirdBehaviourApplication.class})
public class ApiClientTest {

  public static final String JSON =
      "    {\n" +
              "    \"data\":[\n" +
              "            [\n" +
              "            {\n" +
              "                \"string1\": \"Time\",\n" +
              "                \"string2\": \"DateTimeFormat\"\n" +
              "            },\n" +
              "            {\n" +
              "                \"string1\": \"value1\",\n" +
              "                \"string2\": \"value2\",\n" +
              "                \"string3\": \"value3\",\n" +
              "                \"string4\": \"value4\",\n" +
              "                \"string5\": \"value5\"\n" +
              "            },\n" +
              "            {\n" +
              "                \"string1\": \"value1_2\",\n" +
              "                \"string2\": \"value2_2\",\n" +
              "                \"string3\": \"value3_2\",\n" +
              "                \"string4\": \"value4_2\",\n" +
              "                \"string5\": \"value5_2\"\n" +
              "            },\n" +
              "            {\n" +
              "                \"string1\": \"value1_3\",\n" +
              "                \"string2\": \"value2_3\",\n" +
              "                \"string3\": \"value3_3\",\n" +
              "                \"string4\": \"value4_3\",\n" +
              "                \"string5\": \"value5_3\"\n" +
              "            }\n" +
              "        ],\n" +
              "        [\n" +
              "            \"DateTimeString1\",\n" +
              "            12,\n" +
              "            0,\n" +
              "            32.12\n" +
              "        ],\n" +
              "        [\n" +
              "            \"DateTimeString2\",\n" +
              "            14,\n" +
              "            5,\n" +
              "            333.12\n" +
              "        ]\n" +
              "    ]\n" +
              "}";

  @Autowired private ApiClient client;
  @Autowired private MockRestServiceServer server;

  @Test
  public void getChannelDefinitionsForSiteAndTypeProcessesResponseCorrectly() {

    server
        .expect(requestTo("http://localhost:17634/api/v1/test"))
        .andRespond(withSuccess(JSON, MediaType.APPLICATION_JSON));

    MyCustomDto dto = client.queryApi();
    assertThat(dto, instanceOf(MyCustomDto.class));
    assertThat(dto.getElement1().getString1(), is(Matchers.equalTo("Time")));
    assertThat(dto.getElement1().getString2(), is(Matchers.equalTo("DateTimeFormat")));

    assertThat(dto.getElement2List(), hasSize(3));

    Element2 element2 = dto.getElement2List().get(0);
    assertThat(element2.getString1(), is(Matchers.equalTo("value1")));
    assertThat(element2.getString2(), is(Matchers.equalTo("value2")));
    assertThat(element2.getString3(), is(Matchers.equalTo("value3")));
    assertThat(element2.getString4(), is(Matchers.equalTo("value4")));
    assertThat(element2.getString5(), is(Matchers.equalTo("value5")));


    Element2 element2_2 = dto.getElement2List().get(1);
    assertThat(element2_2.getString1(), is(Matchers.equalTo("value1_2")));
    assertThat(element2_2.getString2(), is(Matchers.equalTo("value2_2")));
    assertThat(element2_2.getString3(), is(Matchers.equalTo("value3_2")));
    assertThat(element2_2.getString4(), is(Matchers.equalTo("value4_2")));
    assertThat(element2_2.getString5(), is(Matchers.equalTo("value5_2")));


    Element2 element2_3 = dto.getElement2List().get(2);
    assertThat(element2_3.getString1(), is(Matchers.equalTo("value1_3")));
    assertThat(element2_3.getString2(), is(Matchers.equalTo("value2_3")));
    assertThat(element2_3.getString3(), is(Matchers.equalTo("value3_3")));
    assertThat(element2_3.getString4(), is(Matchers.equalTo("value4_3")));
    assertThat(element2_3.getString5(), is(Matchers.equalTo("value5_3")));

    assertThat(dto.getValusOfTrailingArrays().size(), is(Matchers.equalTo(2)));

    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString1").get(0), is(Matchers.equalTo(new BigDecimal("12"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString1").get(1), is(Matchers.equalTo(new BigDecimal("0"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString1").get(2), is(Matchers.equalTo(new BigDecimal("32.12"))));

    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString2").get(0), is(Matchers.equalTo(new BigDecimal("14"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString2").get(1), is(Matchers.equalTo(new BigDecimal("5"))));
    assertThat(dto.getValusOfTrailingArrays().get("DateTimeString2").get(2), is(Matchers.equalTo(new BigDecimal("333.12"))));
  }
}
