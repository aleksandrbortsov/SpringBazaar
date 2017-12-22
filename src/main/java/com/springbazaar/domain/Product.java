package com.springbazaar.domain;

import com.springbazaar.domain.util.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "SB_PRODUCTS")
public class Product extends StandardEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Product() {
    }

    public Product(String caption, String description, BigDecimal price, String imageUrl, Date createdWhen, BigInteger createdBy) {
        super(createdWhen, createdBy);
        this.caption = caption;
        this.description = description;
        this.price = price;
//        this.imageUrl = imageUrl;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }

//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    //TODO equals , hashCode, toString methods

    @Override
    public String toString() {
        return "Product {" +
                "ID = " + id +
                ", caption = '" + caption + '\'' +
                ", description = '" + description + '\'' +
                ", price = " + price +
                ", person ID = " + person.getId() +
                '}';
    }
}
