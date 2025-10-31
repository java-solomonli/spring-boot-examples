package javasl.simple.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class JsonCtrlTest {

  @Test
  void getPerson() throws Exception {
    var path1 = Paths.get("src/test/resources/person1.json");
    var jsonFile = new ByteArrayInputStream(Files.readAllBytes(path1));

    assertNotNull(jsonFile);
    ObjectMapper mapper = new ObjectMapper();
    Person person = null;
    try {
      person = mapper.readValue(jsonFile, Person.class);
    } catch (Exception e) {
      fail(e);
    }
    assertNotNull(person);
    assertEquals("Daniel", person.givennames());
    assertEquals("Solomon", person.surname());
  }

  @Test
  void insert() {
  }
}
