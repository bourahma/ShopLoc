package com.mimka.shoplocbe.batch.vfp;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;

    private final Job processCustomerOrdersJob;

    @Scheduled(cron = "${batch.scheduled.cron.expression}")
    public void runBatchJob() throws Exception {
        jobLauncher.run(processCustomerOrdersJob, new JobParameters());
    }
}
