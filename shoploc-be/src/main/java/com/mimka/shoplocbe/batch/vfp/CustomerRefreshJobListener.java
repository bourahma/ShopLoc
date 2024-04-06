package com.mimka.shoplocbe.batch.vfp;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRefreshJobListener implements JobExecutionListener {

    private final CustomerOrdersReader customerOrdersReader;

    @Autowired
    public CustomerRefreshJobListener(CustomerOrdersReader customerOrdersReader) {
        this.customerOrdersReader = customerOrdersReader;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        customerOrdersReader.reset();
    }
}
