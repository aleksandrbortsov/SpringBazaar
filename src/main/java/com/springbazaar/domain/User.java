package com.springbazaar.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "SB_USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(unique = true, nullable = false)
    //TODO see into @Email/@NotEmpty
//    @Email(message = "*Please provide a valid email")
//    @NotEmpty(message = "*Please provide an email")
    private String username;

    @Column(unique = true, nullable = false)
//    @Length(min = 5, message = "*Your password must have at least 5 characters")
//    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SB_USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    //TODO add fields
    private boolean state;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Set<Role> roles, Person person) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.person = person;
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    //TODO equals , hashCode, toString methods
    @Override
    public String toString() {
        return "User {ID = " + getId()
                + ", Username = " + getUsername()
                + ", Role {" + getRoles() + "}"
                + ", Person = " + getPerson()
                + "}";
    }
}