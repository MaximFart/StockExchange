package com.tot.system.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class JobBatchConfig {
    public final SecurityBatchConfig securityBatchConfig;

    public final HistoryBatchConfig historyBatchConfig;

    public final JobBuilderFactory jobFactory;

    @Autowired
    public JobBatchConfig(SecurityBatchConfig securityBatchConfig,
                          HistoryBatchConfig historyBatchConfig,
                          JobBuilderFactory jobFactory) {
        this.securityBatchConfig = securityBatchConfig;
        this.historyBatchConfig = historyBatchConfig;
        this.jobFactory = jobFactory;
    }

    @Bean
    public Job securityJob() throws IOException {
        return jobFactory
                .get("securityJob")
                .incrementer(new RunIdIncrementer())
                .flow(securityBatchConfig.securityStep())
                .next(historyBatchConfig.historyStep())
                .end()
                .build();
    }
}
