package com.springbazaar.domain.util;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@MappedSuperclass
public class StandardEntity implements Serializable, Creatable {
    //    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdWhen;

    protected BigInteger createdBy;

    public StandardEntity() {
    }

    public StandardEntity(Date createdWhen, BigInteger createdBy) {
        this.createdWhen = createdWhen;
        this.createdBy = createdBy;
    }

//    protected Date updateWhen;
//
//    protected User updatedBy;
//
//    protected Date deleteWhen;
//
//    protected User deletedBy;

    @Override
    public Date getCreatedWhen() {
        return createdWhen;
    }

    @Override
    public void setCreatedWhen(Date date) {
        this.createdWhen = date;
    }

    @Override
    public BigInteger getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(BigInteger user) {
        this.createdBy = user;
    }

//    @Override
//    public Boolean isDeleted() {
//        return deleteWhen != null;
//    }
//
//    @Override
//    public Date getDeleteWhen() {
//        return deleteWhen;
//    }
//
//    @Override
//    public void setDeleteWhen(Date date) {
//        this.deleteWhen = date;
//    }
//
//    @Override
//    public User getDeletedBy() {
//        return deletedBy;
//    }
//
//    @Override
//    public void setDeletedBy(User user) {
//        this.deletedBy = user;
//    }
//
//    @Override
//    public Date getUpdateWhen() {
//        return updateWhen;
//    }
//
//    @Override
//    public void setUpdateWhen(Date date) {
//        this.updateWhen = date;
//    }
//
//    @Override
//    public User getUpdatedBy() {
//        return updatedBy;
//    }
//
//    @Override
//    public void setUpdatedBy(User user) {
//        this.updatedBy = user;
//    }
}
