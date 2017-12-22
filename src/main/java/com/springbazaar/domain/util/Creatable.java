package com.springbazaar.domain.util;

import java.math.BigInteger;
import java.util.Date;

public interface Creatable {

    Date getCreatedWhen();

    void setCreatedWhen(Date date);

    BigInteger getCreatedBy();

    void setCreatedBy(BigInteger userId);
}
