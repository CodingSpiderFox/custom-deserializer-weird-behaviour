package customdeserializerweirdbehaviour.service;

import customdeserializerweirdbehaviour.CustomdeserializerWeirdBehaviourApplication;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest({ApiClient.class, CustomdeserializerWeirdBehaviourApplication.class})
public class ApiClientTest {

  public static final String JSON =
      "{\n"
          + "    \"data\": [\n"
          + "        [\n"
          + "            {\n"
          + "                \"string1\": \"Time\",\n"
          + "                \"string2\": \"DateTimeFormat\"\n"
          + "            },\n"
          + "            {\n"
          + "                \"string1\": \"value1\",\n"
          + "                \"string2\": \"value2\",\n"
          + "                \"string3\": \"value3\",\n"
          + "                \"string4\": \"value4\",\n"
          + "                \"string5\": \"value5\"\n"
          + "            },\n"
          + "            {\n"
          + "                \"string1\": \"value1_2\",\n"
          + "                \"string2\": \"value2_2\",\n"
          + "                \"string3\": \"value3_2\",\n"
          + "                \"string4\": \"value4_2\",\n"
          + "                \"string5\": \"value5_2\"\n"
          + "            },"
          + "            {\n"
          + "                \"string1\": \"value1_3\",\n"
          + "                \"string2\": \"value2_3\",\n"
          + "                \"string3\": \"value3_3\",\n"
          + "                \"string4\": \"value4_3\",\n"
          + "                \"string5\": \"value5_3\"\n"
          + "            }\n"
          + "        ],\n"
          + "        [\n"
          + "            \"DateTimeString1\",\n"
          + "            12,\n"
          + "            0,\n"
          + "            32.12,\n"
          + "        ],\n"
          + "        [\n"
          + "            \"DateTimeString2\",\n"
          + "            14,\n"
          + "            5,\n"
          + "            333.12,\n"
          + "        ]\n"
          + "    ]\n"
          + "}";

  @Autowired private ApiClient client;
  @Autowired private MockRestServiceServer server;

  @Test
  public void getChannelDefinitionsForSiteAndTypeProcessesResponseCorrectly() {

    server
        .expect(requestTo("http://localhost:17634/api/v1/test"))
        .andRespond(withSuccess(JSON, MediaType.APPLICATION_JSON));

    MyCustomDto dto = client.queryApi();
    assertThat(dto.getElement2List(), hasSize(3));

    // assertThat(dto.getValusOfTrailingArrays().size(), is(equalTo(2)));
  }
}
