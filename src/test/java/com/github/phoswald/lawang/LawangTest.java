package com.github.phoswald.lawang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LawangTest {

    @Test
    public void create_noArgs_defaultValues() {
        Person person = Lawang.create(Person.class);
        assertNotNull(person);
        assertNull(person.name());
        assertNull(person.email());
        assertNull(person.birthdate());
        assertEquals(0, person.age());
    }

    @Test
    public void create_withInitializer_correctValues() {
        Person person = Lawang.create(Person.class, (cx, it) -> cx
            .set(it::name).to("Philip Oswald")
            .set(it::email).to("philip.oswald@gmail.com")
            .set(it::birthdate).to(LocalDate.of(1977, 10, 26))
            .set(it::age).to(40));
        assertNotNull(person);
        assertEquals("Philip Oswald", person.name());
        assertEquals("philip.oswald@gmail.com", person.email());
        assertEquals(LocalDate.of(1977, 10, 26), person.birthdate());
        assertEquals(40, person.age());
    }

    @Test
    public void create_withMap_correctValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Philip Oswald");
        map.put("email", "philip.oswald@gmail.com");
        map.put("birthdate", LocalDate.of(1977, 10, 26));
        map.put("age", 40);
        Person person = Lawang.create(Person.class, map);
        assertNotNull(person);
        assertEquals("Philip Oswald", person.name());
        assertEquals("philip.oswald@gmail.com", person.email());
        assertEquals(LocalDate.of(1977, 10, 26), person.birthdate());
        assertEquals(40, person.age());
    }

    @Test
    public void toMap_instance_correctValues() {
        Person person = Lawang.create(Person.class, (cx, it) -> cx
                .set(it::name).to("Philip Oswald")
                .set(it::age).to(40));
        Map<String, Object> map = Lawang.toMap(person);
        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals("Philip Oswald", map.get("name"));
        assertEquals(40, map.get("age"));
    }

    @Test
    public void instance_toString_byValue() {
        Person person = Lawang.create(Person.class, (cx, it) -> cx
            .set(it::name).to("Philip Oswald")
            .set(it::email).to("philip.oswald@gmail.com"));

        assertEquals("Person {email=philip.oswald@gmail.com, name=Philip Oswald}", person.toString());
    }

    @Test
    public void instance_equalsAndHashCode_byValue() {
        Person person = Lawang.create(Person.class, (cx, it) -> cx
            .set(it::name).to("Philip Oswald")
            .set(it::email).to("philip.oswald@gmail.com"));
        Person person2 = Lawang.create(Person.class, (cx, it) -> cx
            .set(it::name).to("Philip Oswald")
            .set(it::email).to("philip.oswald@gmail.com"));
        Person person3 = Lawang.create(Person.class, (cx, it) -> cx
            .set(it::name).to("Philip Oswald"));

        assertTrue(person.equals(person2));
        assertEquals(person.hashCode(), person2.hashCode());
        assertFalse(person.equals(person3));
        assertNotEquals(person.hashCode(), person3.hashCode());
        assertFalse(person.equals(person.toString()));
        assertFalse(person.equals(null));
    }
}
