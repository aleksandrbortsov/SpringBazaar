package com.springbazaar.domain;

import com.springbazaar.domain.util.FullName;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "sb_persons")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

    public Person(User user, FullName fullName, List<Product> products) {
        this.user = user;
        this.fullName = fullName;
        this.products = products;
    }

    public Person(FullName fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return getFullName().getFirstName() + " "
                + (StringUtils.isNotEmpty(getFullName().getMiddleName()) ? getFullName().getMiddleName() : "");
    }
}
