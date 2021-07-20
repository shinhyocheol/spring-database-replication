package com.replication.app.config.database.datasource;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        
        log.debug("Transaction isReadOnly : {}", isReadOnly);
        
        if(isReadOnly) {
            return "slave";
        } else {
            return "master";
        }
    }
    

}
