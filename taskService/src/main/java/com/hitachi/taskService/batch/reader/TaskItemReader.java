package com.hitachi.taskService.batch.reader;

import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.repository.TaskRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
public class TaskItemReader {

    @Autowired
    private TaskRepository taskRepository;

    public ItemReader<Task> reader() {
        return new RepositoryItemReaderBuilder<Task>()
                .repository(taskRepository)
                .methodName("findByStatusAndCompletedAtBefore")
                .arguments(String.valueOf(Task.Status.COMPLETED.ordinal()), LocalDateTime.now().minusDays(7))
                .pageSize(10)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .name("taskItemReader")
                .build();
    }
}
