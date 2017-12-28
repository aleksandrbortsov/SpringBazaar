package com.springbazaar.domain.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StandardEntity implements Serializable, Creatable {
    protected Date createdWhen;
    protected BigInteger createdBy;

    public StandardEntity(Date createdWhen, BigInteger createdBy) {
        this.createdWhen = createdWhen;
        this.createdBy = createdBy;
    }
//TODO add update & delete actions
//    protected Date updateWhen;
//
//    protected User updatedBy;
//
//    protected Date deleteWhen;
//
//    protected User deletedBy;

}
