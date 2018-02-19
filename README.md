# Lawang - A small island off Lombok

Define immutable value types using interfaces and method references

## Sample usage

    public interface Person {
        String name();
        String email();
        LocalDate birthdate();
        int age();
    }
    
    Person person = Lawang.create(Person.class, (b, it) -> b
        .set(it::name).to("Philip Oswald")
        .set(it::age).to(40));
    
    assertNotNull(person);
    assertEquals("Philip Oswald", person.name());
    assertEquals(40, person.age());

## Design goals

* immutability
* type safety
* simplicity
* no external dependencies (rely on JRE only)
* no byte code manipulation or dynamic code generation (no Java agent, rely on JRE dynamic proxies only)
* ease of use (concise syntax, built-in support for common JRE types and file formats)

## Intended feature set

Supported data types:
* Java primitive types: `boolean`, `byte`, `short`, `char`, `int`, `long`, `float`, `double`
* Boxed primitive types: `java.lang.Boolean`, ...
* Enums
* Common immutable types: `java.lang.String`, `java.math.BigDecimal`, `java.math.BigInteger`, 
  `java.lang.Class`, `java.util.regex.Pattern`, `java.nio.file.Path`
* Immutable date/time types: `java.time.LocalDateTime`, ...
* Immutable collections and optionals: `java.util.List<T>`, `java.util.SortedSet<T>`, `java.util.SortedMap<String,T>`
  and `java.lang.Optional<T>` (where `T` is a supported data type)
* other compatible interfaces (i.e. interfaces where all non-static, non-abstract methods are getters that 
  return a supported data type).

Supported functionality:
* `toString()`, `hashCode()`, `equals()` with 'by value' semantics
* shallow conversion from and to `Map<String,Object>` (where `Object` is a supported data type)
* deep conversion from and to `Map<String,Object>` (where `Object` is `String` or `Map<String,Object>`) 
* conversion from and to XML and JSON
* support for Java serialization (`java.io.Serializable`)
