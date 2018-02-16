package com.github.phoswald.lawang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

public class LawangTest {
	
	@Test
	public void builder_noFields_defaultValues() {
		Person person = Lawang.build(Person.class);
		assertNotNull(person);
		assertNull(person.name());
		assertNull(person.email());
	}
	
	@Test
	public void builder_withFields_correctValues() {
		Person person = Lawang.builder(Person.class)
				.with(Person::name, "Philip Oswald")
				.with(Person::email, "philip.oswald@gmail.com")
				.with(Person::birthdate, LocalDate.of(1977, 10, 26))
				.build();
		assertNotNull(person);
		assertEquals("Philip Oswald", person.name());
		assertEquals("philip.oswald@gmail.com", person.email());
		assertEquals(LocalDate.of(1977, 10, 26), person.birthdate());
		
		assertEquals("{birthdate=1977-10-26, email=philip.oswald@gmail.com, name=Philip Oswald}", person.toString());
		assertEquals(person.toString().hashCode(), person.hashCode());
	}
	
	// TODO: type safety of builder is not enforced by compiler
}
