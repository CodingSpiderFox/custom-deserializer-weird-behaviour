package customdeserializerweirdbehaviour.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import customdeserializerweirdbehaviour.dto.MyCustomDto;
import lombok.SneakyThrows;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ApiClient {

  private final RestTemplate restTemplate;

  public ApiClient(RestTemplateBuilder restTemplateBuilder) {
    restTemplate = restTemplateBuilder.build();
  }

  @SneakyThrows
  public MyCustomDto queryApi() {

    String json =
        restTemplate
            .exchange(
                RequestEntity.get(new URI("http://localhost:17634/api/v1/test")).build(),
                String.class)
            .getBody();

    return new ObjectMapper().readValue(json, MyCustomDto.class);
  }
}
