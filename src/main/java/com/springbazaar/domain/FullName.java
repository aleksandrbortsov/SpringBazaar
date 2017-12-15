package com.springbazaar.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FullName implements Serializable {

    @NotEmpty(message = "*Please provide your name")
    private String firstName;
    private String middleName;
    private String lastName;

    public FullName() {
    }

    public FullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "{" + getFirstName() + " " + getMiddleName() + " " + getLastName() + "}";
    }
}
