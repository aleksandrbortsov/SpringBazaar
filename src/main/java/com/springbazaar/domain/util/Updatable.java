package com.springbazaar.domain.util;

import com.springbazaar.domain.User;

import java.util.Date;

public interface Updatable {

    Date getUpdateWhen();

    void setUpdateWhen(Date date);

    User getUpdatedBy();

    void setUpdatedBy(User user);
}
