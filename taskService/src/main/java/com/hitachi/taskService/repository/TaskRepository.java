package com.hitachi.taskService.repository;

import com.hitachi.taskService.entity.Task;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;


@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE CAST(t.status AS string) = :status AND t.completedAt < :dateTime")
    Slice<Task> findByStatusAndCompletedAtBefore(@Param("status") String status, @Param("dateTime") LocalDateTime dateTime, Pageable pageable);
}
