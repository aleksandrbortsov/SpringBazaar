package com.springbazaar.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "sb_users")
public class User implements Serializable {

    private BigInteger id;
    private String login;
    private String password;
    private Role role;
    private Person person;


    public User() {
    }

    public User(BigInteger id, String login, String password, Role role, Person person, List<Product> products) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.person = person;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(unique = true, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Column(name = "role_id", unique = true, nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToOne(mappedBy = "sb_users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}