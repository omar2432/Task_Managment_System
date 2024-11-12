package com.hitachi.taskService.batch.writer;

import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.repository.TaskRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskItemWriter {

    @Autowired
    private TaskRepository taskRepository;

    public ItemWriter<Task> writer() {
        return tasks -> taskRepository.saveAll(tasks);
    }
}
