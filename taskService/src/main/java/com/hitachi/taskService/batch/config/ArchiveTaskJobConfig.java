package com.hitachi.taskService.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ArchiveTaskJobConfig {

    @Bean
    public Job archiveTasksJob(JobRepository jobRepository, Step archiveTasksStep) {
        return new JobBuilder("archiveTasksJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(archiveTasksStep)
                .build();
    }
}
