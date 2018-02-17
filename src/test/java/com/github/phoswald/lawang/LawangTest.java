package com.github.phoswald.lawang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class LawangTest {

	@Test
	public void builder_noFields_defaultValues() {
		Person person = Lawang.build(Person.class);
		assertNotNull(person);
		assertNull(person.name());
		assertNull(person.email());
		assertNull(person.birthdate());
		assertEquals(0, person.age());
	}

	@Test
	public void builder_withFields_correctValues() {
		Person person = Lawang.builder(Person.class)
				.with(Person::name, "Philip Oswald")
				.with(Person::email, "philip.oswald@gmail.com")
				.with(Person::birthdate, LocalDate.of(1977, 10, 26))
				.with(Person::age, 40)
				.build();
		assertNotNull(person);
		assertEquals("Philip Oswald", person.name());
		assertEquals("philip.oswald@gmail.com", person.email());
		assertEquals(40, person.age());
		assertEquals(LocalDate.of(1977, 10, 26), person.birthdate());
	}

	@Test
	public void builder_toString_byValue() {
		Person person = Lawang.builder(Person.class)
				.with(Person::name, "Philip Oswald")
				.with(Person::email, "philip.oswald@gmail.com")
				.build();

		assertEquals("Person {email=philip.oswald@gmail.com, name=Philip Oswald}", person.toString());
	}

	@Test
	public void builder_equalsAndHashCode_byValue() {
		Person person = Lawang.builder(Person.class)
				.with(Person::name, "Philip Oswald")
				.with(Person::email, "philip.oswald@gmail.com")
				.build();
		Person person2 = Lawang.builder(Person.class)
				.with(Person::name, "Philip Oswald")
				.with(Person::email, "philip.oswald@gmail.com")
				.build();
		Person person3 = Lawang.builder(Person.class)
				.with(Person::name, "Philip Oswald")
				.build();

		assertTrue(person.equals(person2));
		assertEquals(person.hashCode(), person2.hashCode());
		assertFalse(person.equals(person3));
		assertNotEquals(person.hashCode(), person3.hashCode());
		assertFalse(person.equals(person.toString()));
		assertFalse(person.equals(null));
	}

	// TODO: type safety of builder is not enforced by compiler
}
