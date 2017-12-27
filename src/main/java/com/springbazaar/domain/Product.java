package com.springbazaar.domain;

import com.springbazaar.domain.util.StandardEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "SB_PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Product extends StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Size(min = 5, max = 50)
    private String caption;

    @Size(max = 500)
    private String description;

    @Max(1000000)
    private BigDecimal price;

    //    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    public Product(String caption, String description, BigDecimal price, String imageUrl, Date createdWhen, BigInteger createdBy) {
        super(createdWhen, createdBy);
        this.caption = caption;
        this.description = description;
        this.price = price;
//        this.imageUrl = imageUrl;
    }
}
