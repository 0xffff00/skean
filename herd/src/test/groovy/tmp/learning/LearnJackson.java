package tmp.learning;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class LearnJackson {

	static ObjectMapper objMapper;

	@BeforeClass
	public static void beforeClass() {
		objMapper = new ObjectMapper();
	}

	@Test
	public void learnDateFormat() throws Exception {
		assert "fdew3".matches("\\w+");
		LocalDateTime t1=LocalDateTime.now();
		 // objMapper.setDefaultTyping("").writerWithDefaultPrettyPrinter().writeValue(System.out, t1);
	}
	
	//@Test
	public void learnPolymorphismDeserialization() throws JsonParseException, JsonMappingException, IOException {
		
		Zoo zoo = objMapper.readValue(JsonData.getZoo1(), Zoo.class);
		System.out.println(zoo);
		/*
		Zoo zoo = new Zoo("Samba Wild Park", "Paz");
        Lion lion = new Lion("Simba");
        Elephant elephant = new Elephant("Manny");
        List<Animal> animals = Arrays.asList(lion,elephant);
        zoo.setAnimals(animals);
 
        objMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, zoo);
        */
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = As.PROPERTY, property = "@class")
class Zoo {

	public String name;
	public String city;
	public List<Animal> animals;

	@JsonCreator
	public Zoo(@JsonProperty("name") String name, @JsonProperty("city") String city) {
		this.name = name;
		this.city = city;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	@Override
	public String toString() {
		return "Zoo [name=" + name + ", city=" + city + ", animals=" + animals + "]";
	}

}

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "@class")
@JsonSubTypes({ @Type(value = Lion.class, name = "lion"), @Type(value = Elephant.class, name = "elephant") })
abstract class Animal {
	@JsonProperty("name")
	String name;
	@JsonProperty("sound")
	String sound;
	@JsonProperty("type")
	String type;
	@JsonProperty("endangered")
	boolean endangered;

}

class Lion extends Animal {

	private String name;

	@JsonCreator
	public Lion(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSound() {
		return "Roar";
	}

	public String getType() {
		return "carnivorous";
	}

	public boolean isEndangered() {
		return true;
	}

	@Override
	public String toString() {
		return "Lion [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()="
				+ getType() + ", isEndangered()=" + isEndangered() + "]";
	}

}

class Elephant extends Animal {

	@JsonProperty
	private String name;

	@JsonCreator
	public Elephant(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSound() {
		return "trumpet";
	}

	public String getType() {
		return "herbivorous";
	}

	public boolean isEndangered() {
		return false;
	}

	@Override
	public String toString() {
		return "Elephant [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()="
				+ getType() + ", isEndangered()=" + isEndangered() + "]";
	}

}