package com.springbazaar.domain.util;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class FullName implements Serializable {
    @Size(min = 5, max = 50)
    @NotNull
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @Size(min = 5, max = 50)
    @NotNull
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
        return "{" + getFirstName() + ", " + getMiddleName() + ", " + getLastName() + "}";
    }
}
