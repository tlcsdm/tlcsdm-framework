package com.tlcsdm.framework.transaction.support;

import com.tlcsdm.framework.transaction.TransactionDefinition;

import java.io.Flushable;
import java.sql.Connection;

public interface TransactionObject extends Flushable {
    Connection getConnection();

    Integer getCount();

    TransactionDefinition getTransactionDefinition();

    boolean isRollback();

    void setRollback(boolean isRollback);

    void whetherToRollBack(Throwable t);
}
