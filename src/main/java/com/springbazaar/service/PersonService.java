package com.springbazaar.service;

import com.springbazaar.domain.Person;

import java.math.BigInteger;

public interface PersonService {
    Person getById(BigInteger id);
}
