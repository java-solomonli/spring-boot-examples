package javasl.simple.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class JsonCtrl {

  private static final String FILE_NAME = "classpath:io/daniel.json";

  @Autowired
  private ApplicationContext ctx;

  @Autowired
  private ObjectMapper mapper;

  @Value(FILE_NAME)
  private Resource jsonFile;

  @GetMapping
  public Person getPerson() {
    return new Person("Daniel", "Solomon");
  }

  @GetMapping("/daniel")
  public Person getDaniel() {
    Resource resource = ctx.getResource(FILE_NAME);
    try (InputStream json = resource.getInputStream()) {
      return mapper.readValue(json, Person.class);
    } catch (Exception e) {
      return new Person("", "");
    }
  }

  @GetMapping("/daniel2")
  public Person getDaniel2() {
    try (InputStream json = jsonFile.getInputStream()) {
      return mapper.readValue(json, Person.class);
    } catch (Exception e) {
      return new Person("", "");
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public int insert(Person person) {
    int id=1;

    return id;
  }


}
