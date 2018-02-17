package com.github.phoswald.lawang;

import java.time.LocalDate;

public interface Person {

	String name();
	String email();
	LocalDate birthdate();
	int age();
}