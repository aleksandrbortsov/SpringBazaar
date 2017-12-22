package com.springbazaar.domain.util;

import com.springbazaar.domain.User;

import java.util.Date;

public interface Deletable {

    Boolean isDeleted();

    Date getDeleteWhen();

    void setDeleteWhen(Date date);

    User getDeletedBy();

    void setDeletedBy(User user);
}
