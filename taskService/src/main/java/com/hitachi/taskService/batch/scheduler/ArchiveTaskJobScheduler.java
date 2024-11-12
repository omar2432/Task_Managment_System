package com.hitachi.taskService.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ArchiveTaskJobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job archiveTasksJob;


    //    @Scheduled(cron = "0 * * * * ?") // Runs every minute
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void runJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution execution = jobLauncher.run(archiveTasksJob, params);
            System.out.println("Batch job executed successfully with status: " + execution.getStatus());
        } catch (Exception e) {
            System.out.println("Exception while running the task archive job: " + e);
        }
    }
}
