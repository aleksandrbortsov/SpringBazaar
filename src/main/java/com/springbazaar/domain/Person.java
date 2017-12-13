package com.springbazaar.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "sb_persons")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Product> products;

    public Person() {
    }

    public Person(User user, FullName fullName, List<Product> products) {
        this.user = user;
        this.fullName = fullName;
        this.products = products;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Person {ID = " + getId()
                + ", Full name = " + getFullName()
                + ", Login = " + getUser().getLogin()
                + ", Products = " + getProducts()
                + "}";
    }
}
