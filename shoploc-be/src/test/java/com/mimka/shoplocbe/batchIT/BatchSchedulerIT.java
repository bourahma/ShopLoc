package com.mimka.shoplocbe.batchIT;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@EnableBatchProcessing
@Testcontainers
class BatchSchedulerIT {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processCustomerOrdersJob;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    static void setUp() {
        postgresqlContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgresqlContainer.stop();
    }

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Test
    @Rollback
    void testVFPBatch_ReturnCOMPLETED() throws Exception {

        testBatchVFP(1, 9, true, true);
        testBatchVFP(2, 2, true, false);
        testBatchVFP(3, 5, false, false);

        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJob(processCustomerOrdersJob);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        testBatchVFP(1, 9, true,false);
        testBatchVFP(2, 2, false, false);
        testBatchVFP(3, 5, true, false);
    }

    private void testBatchVFP(int customerId, int expectedOrderCount, boolean expectedVfpMembership, boolean expectedVfpUsed) throws Exception {
        Boolean vfpUsed = jdbcTemplate.queryForObject(
                "SELECT vfp_used FROM Customer WHERE id = ?", Boolean.class, customerId);
        Assertions.assertEquals(expectedVfpUsed, vfpUsed);

        Integer orderCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Orders WHERE customer_id = ? AND order_date >= CURRENT_DATE - INTERVAL '7 days'",
                Integer.class, customerId);
        Assertions.assertEquals(expectedOrderCount, orderCount);

        Boolean isVfpMembership = jdbcTemplate.queryForObject(
                "SELECT is_vfp_membership FROM Customer WHERE id = ?", Boolean.class, customerId);
        Assertions.assertEquals(expectedVfpMembership, isVfpMembership);
    }


}
