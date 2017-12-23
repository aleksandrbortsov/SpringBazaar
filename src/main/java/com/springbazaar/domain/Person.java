package com.springbazaar.domain;

import com.springbazaar.domain.util.FullName;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "sb_persons")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "middleName", column = @Column(name = "middle_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    private FullName fullName;

    private String email;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public Person() {
    }

    public Person(User user, FullName fullName, List<Product> products) {
        this.user = user;
        this.fullName = fullName;
        this.products = products;
    }

    public Person(FullName fullName) {
        this.fullName = fullName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return getFullName().getFirstName() + " "
                + (StringUtils.isNotEmpty(getFullName().getMiddleName()) ? getFullName().getMiddleName() : "");
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //TODO equals , hashCode, toString methods
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Person ");
        stringBuilder.append("{ID = ").append(id);
        stringBuilder.append(", full name = ").append(fullName);
        if (StringUtils.isNotEmpty(email) && email.equals(user.getUsername())) {
            stringBuilder.append(", username/email = ").append(user.getUsername());
        } else {
            stringBuilder.append(", username = ").append(user.getUsername());
            stringBuilder.append(", email = ").append(email);
        }
        stringBuilder.append(", products = {").append(getProducts()).append("}");
        stringBuilder.append("}");
        return String.valueOf(stringBuilder);
    }
}
