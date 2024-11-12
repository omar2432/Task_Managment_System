package com.hitachi.taskService.batch.processor;

import com.hitachi.taskService.entity.Task;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TaskItemProcessor {

    public ItemProcessor<Task, Task> processor() {
        return task -> {
            task.setStatus(Task.Status.ARCHIVED);
            return task;
        };
    }
}
