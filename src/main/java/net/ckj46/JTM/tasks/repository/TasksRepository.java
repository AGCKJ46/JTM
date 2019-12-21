package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.tasks.entity.Task;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface TasksRepository {
    void add(Task task);

    List<Task> fetchAll();

    Task fetchById(Long id);

    void update(Long id, String title, String description, String project, int prio, LocalDateTime editedAt);

    void deleteById(Long id);

    void save(Task task);
}