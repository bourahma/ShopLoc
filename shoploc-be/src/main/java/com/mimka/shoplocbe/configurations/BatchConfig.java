package com.mimka.shoplocbe.configurations;

import com.mimka.shoplocbe.batch.vfp.CustomerOrdersReader;
import com.mimka.shoplocbe.batch.vfp.CustomerWriter;
import com.mimka.shoplocbe.batch.vfp.VFPStatusProcessor;
import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final CustomerOrdersReader customerOrdersReader;

    private final VFPStatusProcessor vfpStatusProcessor;

    private final CustomerWriter customerWriter;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public Job processVFPStatusJob (JobRepository jobRepository) {
        return new JobBuilder("VFP Status JOB", jobRepository)
                .flow(processVFPStatusStep(jobRepository)).end().build();
    }

    @Bean
    public Step processVFPStatusStep (JobRepository jobRepository) {
        this.customerOrdersReader.init();
        return new StepBuilder("VFP Status STEP", jobRepository)
                .<Pair<List<Order>, Customer>, Customer> chunk(10, transactionManager())
                .allowStartIfComplete(true)
                .reader(this.customerOrdersReader)
                .processor(this.vfpStatusProcessor)
                .writer(this.customerWriter)
                .build();
    }
}

