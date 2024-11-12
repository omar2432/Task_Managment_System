package com.hitachi.taskService.batch.config;

import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.batch.reader.TaskItemReader;
import com.hitachi.taskService.batch.processor.TaskItemProcessor;
import com.hitachi.taskService.batch.writer.TaskItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ArchiveTaskStepConfig {

    @Bean
    public Step archiveTasksStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 TaskItemReader taskItemReader,
                                 TaskItemProcessor taskItemProcessor,
                                 TaskItemWriter taskItemWriter) {
        return new StepBuilder("archiveTasksStep", jobRepository)
                .<Task, Task>chunk(10, transactionManager)
                .reader(taskItemReader.reader())
                .processor(taskItemProcessor.processor())
                .writer(taskItemWriter.writer())
                .build();
    }
}
